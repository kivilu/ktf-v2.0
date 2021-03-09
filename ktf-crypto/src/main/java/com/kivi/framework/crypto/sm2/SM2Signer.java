package com.kivi.framework.crypto.sm2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

public class SM2Signer {
	private static BigInteger	n		= new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "7203DF6B" + "21C6052B" + "53BBF409" + "39D54123", 16);
	private static BigInteger	p		= new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFF", 16);
	private static BigInteger	a		= new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFC", 16);
	private static BigInteger	b		= new BigInteger(
			"28E9FA9E" + "9D9F5E34" + "4D5A9E4B" + "CF6509A7" + "F39789F5" + "15AB8F92" + "DDBCBD41" + "4D940E93", 16);
	private static BigInteger	gx		= new BigInteger(
			"32C4AE2C" + "1F198119" + "5F990446" + "6A39C994" + "8FE30BBF" + "F2660BE1" + "715A4589" + "334C74C7", 16);
	private static BigInteger	gy		= new BigInteger(
			"BC3736A2" + "F4F6779C" + "59BDCEE3" + "6B692153" + "D0A9877C" + "C62A4740" + "02DF32E5" + "2139F0A0", 16);
	private static ECCurve.Fp	curve;
	private static ECPoint		G;
	private static SecureRandom	random	= new SecureRandom();

	public SM2Signer() {
		curve	= new ECCurve.Fp(p,			// q
				a,							// a
				b,							// b
				null, null);
		G		= curve.createPoint(gx, gy);
	}

	/**
	 * sm3哈希签名
	 * 
	 * @param M       签名信息
	 * @param IDA     签名方唯一标识
	 * @param keyPair 签名方密钥对
	 * @return 签名
	 */
	public Signature sign(byte[] M, byte[] IDA, BigInteger d) {
		byte[]		ZA	= ZA(IDA, publicKey(d));
		byte[]		M_	= join(ZA, M);
		BigInteger	e	= new BigInteger(1, sm3hash(M_));
		BigInteger	k;
		BigInteger	r;
		do {
			k = random(n);
			ECPoint		p1	= G.multiply(k).normalize();
			BigInteger	x1	= p1.getXCoord().toBigInteger();
			r	= e.add(x1);
			r	= r.mod(n);
		} while (r.equals(BigInteger.ZERO) || r.add(k).equals(n));

		BigInteger s = ((d.add(BigInteger.ONE).modInverse(n)).multiply((k.subtract(r.multiply(d))).mod(n))).mod(n);

		return new Signature(r, s);
	}

	/**
	 * sm3哈希验签
	 * 
	 * @param M        签名信息
	 * @param sign     签名
	 * @param IDA      签名方唯一标识
	 * @param pubKeyXY 签名方公钥
	 * @return true or false
	 */
	public boolean verify(byte[] M, Signature signature, byte[] IDA, String pubKeyXY) {
		ECPoint aPublicKey = publicKey(pubKeyXY);

		if (!between(signature.r, BigInteger.ONE, n))
			return false;
		if (!between(signature.s, BigInteger.ONE, n))
			return false;

		byte[]		M_	= join(ZA(IDA, aPublicKey), M);
		BigInteger	e	= new BigInteger(1, sm3hash(M_));
		BigInteger	t	= signature.r.add(signature.s).mod(n);

		if (t.equals(BigInteger.ZERO))
			return false;

		ECPoint		p1	= G.multiply(signature.s).normalize();
		ECPoint		p2	= aPublicKey.multiply(t).normalize();
		BigInteger	x1	= p1.add(p2).normalize().getXCoord().toBigInteger();
		BigInteger	R	= e.add(x1).mod(n);
		if (R.equals(signature.r))
			return true;
		return false;
	}

	/**
	 * 无哈希签名
	 * 
	 * @param M
	 * @param d
	 * @return
	 */
	public Signature signNoHash(byte[] M, BigInteger d) {
		BigInteger		e					= new BigInteger(1, M);

		BigInteger		r, s;

		ECMultiplier	basePointMultiplier	= createBasePointMultiplier();
		// 5.2.1 Draft RFC: SM2 Public Key Algorithms
		do // generate s
		{
			BigInteger k;
			do // generate r
			{
				// A3
				k = random(n);

				// A4
				ECPoint p = basePointMultiplier.multiply(G, k).normalize();

				// A5
				r = e.add(p.getAffineXCoord().toBigInteger()).mod(n);

			} while (r.equals(BigInteger.ZERO) || r.add(k).equals(n));

			// BigInteger s =
			// ((d.add(BigInteger.ONE).modInverse(n)).multiply((k.subtract(r.multiply(d))).mod(n))).mod(n);
			// A6
			BigInteger dPlus1ModN = d.add(BigInteger.ONE).modInverse(n);

			s	= k.subtract(r.multiply(d)).mod(n);
			s	= dPlus1ModN.multiply(s).mod(n);
		} while (s.equals(BigInteger.ZERO));

		return new Signature(r, s);
	}

	/**
	 * 无哈希验签
	 * 
	 * @param M        签名信息
	 * @param sign     签名
	 * @param pubKeyXY 签名方公钥
	 * @return true or false
	 */
	public boolean verifyNoHash(byte[] M, Signature signature, ECPoint aPublicKey) {

		// 5.3.1 Draft RFC: SM2 Public Key Algorithms
		// B1
		if (!between(signature.r, BigInteger.ONE, n))
			return false;

		// B2
		if (!between(signature.s, BigInteger.ONE, n))
			return false;

		// B4
		BigInteger	e	= new BigInteger(1, M);
		// B5
		BigInteger	t	= signature.r.add(signature.s).mod(n);
		if (t.equals(BigInteger.ZERO))
			return false;

		// B6
		// ECPoint p1 = G.multiply(signature.s).normalize();
		// ECPoint p2 = aPublicKey.multiply(t).normalize();
		// BigInteger x1 = p1.add(p2).normalize().getXCoord().toBigInteger();
		ECPoint x1y1 = ECAlgorithms.sumOfTwoMultiplies(G, signature.s, aPublicKey, t).normalize();
		if (x1y1.isInfinity()) {
			return false;
		}

		// B7
		BigInteger expectedR = e.add(x1y1.getAffineXCoord().toBigInteger()).mod(n);

		return expectedR.equals(signature.r);
	}

	private ECPoint publicKey(String pubKeyXY) {
		if (pubKeyXY.length() != 128 && pubKeyXY.length() != 130)
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥数据长度不正确");
		int			pos		= pubKeyXY.length() == 128 ? 0 : 2;

		String		hexX	= StringUtils.substring(pubKeyXY, pos, 64);
		String		hexY	= StringUtils.substring(pubKeyXY, pos + 64);

		BigInteger	x		= new BigInteger(hexX, 16);
		BigInteger	y		= new BigInteger(hexY, 16);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			baos.write(0x04);
			baos.write(x.toByteArray());
			baos.write(y.toByteArray());
			return curve.decodePoint(baos.toByteArray());
		} catch (IOException e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥无效", e);
		}

	}

	/**
	 * 计算公钥
	 * 
	 * @return
	 */
	private ECPoint publicKey(BigInteger d) {
		ECPoint publicKey = G.multiply(d).normalize();
		if (checkPublicKey(publicKey)) {
			return publicKey;
		} else {
			return null;
		}
	}

	/**
	 * 字节数组拼接
	 * 
	 * @param params
	 * @return
	 */
	private static byte[] join(byte[]... params) {
		byte[] res = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			for (int i = 0; i < params.length; i++) {
				baos.write(params[i]);
			}
			res = baos.toByteArray();
		} catch (IOException e) {
			throw new KtfException(KtfError.E_CRYPTO, "字节数组拼接异常", e);
		}
		return res;
	}

	/**
	 * sm3摘要
	 * 
	 * @param params
	 * @return
	 */
	private static byte[] sm3hash(byte[]... params) {
		return SM3Kit.sm3(join(params));
	}

	/**
	 * 取得用户标识字节数组
	 * 
	 * @param IDA
	 * @param aPublicKey
	 * @return
	 */
	private static byte[] ZA(byte[] IDA, ECPoint aPublicKey) {
		byte[]	idaBytes	= IDA;
		int		entlenA		= idaBytes.length * 8;
		byte[]	ENTLA		= new byte[] { (byte) (entlenA & 0xFF00), (byte) (entlenA & 0x00FF) };
		byte[]	ZA			= sm3hash(ENTLA, idaBytes, a.toByteArray(), b.toByteArray(), gx.toByteArray(),
				gy.toByteArray(), aPublicKey.getXCoord().toBigInteger().toByteArray(),
				aPublicKey.getYCoord().toBigInteger().toByteArray());
		return ZA;
	}

	/**
	 * 判断生成的公钥是否合法
	 * 
	 * @param publicKey
	 * @return
	 */
	private boolean checkPublicKey(ECPoint publicKey) {
		if (!publicKey.isInfinity()) {
			BigInteger	x	= publicKey.getXCoord().toBigInteger();
			BigInteger	y	= publicKey.getYCoord().toBigInteger();

			if (between(x, new BigInteger("0"), p) && between(y, new BigInteger("0"), p)) {

				BigInteger	xResult	= x.pow(3).add(a.multiply(x)).add(b).mod(p);
				BigInteger	yResult	= y.pow(2).mod(p);

				if (yResult.equals(xResult) && publicKey.multiply(n).isInfinity()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否在范围内
	 * 
	 * @param param
	 * @param min
	 * @param max
	 * @return
	 */
	private boolean between(BigInteger param, BigInteger min, BigInteger max) {
		if (param.compareTo(min) >= 0 && param.compareTo(max) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 随机数生成器
	 * 
	 * @param max
	 * @return
	 */
	private static BigInteger random(BigInteger max) {

		BigInteger r = new BigInteger(256, random);

		while (r.compareTo(max) >= 0) {
			r = new BigInteger(128, random);
		}

		return r;
	}

	protected ECMultiplier createBasePointMultiplier() {
		return new FixedPointCombMultiplier();
	}

	public static class Signature {
		BigInteger	r;
		BigInteger	s;

		public Signature(byte[] rs) {
			this(ByteStringKit.toHex(rs));
		}

		public Signature(final String rsHex) {
			String	hexR	= StringUtils.substring(rsHex, 0, 64);
			String	hexS	= StringUtils.substring(rsHex, 64);

			this.r	= new BigInteger(hexR, 16);
			this.s	= new BigInteger(hexS, 16);
		}

		public Signature(BigInteger r, BigInteger s) {
			this.r	= r;
			this.s	= s;
		}

		public String toString() {
			return StringUtils.join(r.toString(16), s.toString(16));
		}

		public byte[] toBytes() {
			return ByteStringKit.fromHex(this.toString());
		}

	}

}
