package com.kivi.framework.crypto.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertificateException;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.util.encoders.Hex;

import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.sm2.Sm2PrivateKey;
import com.kivi.framework.crypto.sm2.Sm2PublicKey;
import com.kivi.framework.crypto.sm2.Sm2Util;

/**
 * need jars: bcpkix-jdk15on-160.jar bcprov-jdk15on-160.jar
 *
 * ref: https://tools.ietf.org/html/draft-shen-sm2-ecdsa-02
 * http://gmssl.org/docs/oid.html http://www.jonllen.com/jonllen/work/164.aspx
 *
 * 用BC的注意点：
 * 这个版本的BC对SM3withSM2的结果为asn1格式的r和s，如果需要直接拼接的r||s需要自己转换。下面rsAsn1ToPlainByteArray、rsPlainByteArrayToAsn1就在干这事。
 * 这个版本的BC对SM2的结果为C1||C2||C3，据说为旧标准，新标准为C1||C3||C2，用新标准的需要自己转换。下面changeC1C2C3ToC1C3C2、changeC1C3C2ToC1C2C3就在干这事。
 */
public class GmUtil {

	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			CertPathBuilderException, InvalidKeyException, SignatureException, CertificateException {

//        // 随便看看 ---------------------
//        System.out.println("GMNamedCurves: ");
//        for(Enumeration e = GMNamedCurves.getNames(); e.hasMoreElements();) {
//            System.out.println(e.nextElement());
//        }
//        System.out.println("sm2p256v1 n:"+x9ECParameters.getN());
//        System.out.println("sm2p256v1 nHex:"+Hex.toHexString(x9ECParameters.getN().toByteArray()));

		// 生成公私钥对 ---------------------
		KeyPairResult kp = Sm2Util.generateKeyPair();

		System.out.println(Hex.toHexString(kp.getPri().getEncoded()));
		System.out.println(Hex.toHexString(kp.getPub().getEncoded()));

		System.out.println(kp.getPri().getAlgorithm());
		System.out.println(kp.getPub().getAlgorithm());

		System.out.println(kp.getPri().getFormat());
		System.out.println(kp.getPub().getFormat());

		System.out.println("private key d: " + ((BCECPrivateKey) kp.getPri()).getD());
		System.out.println("public key q:" + ((BCECPublicKey) kp.getPub()).getQ()); // {x, y, zs...}

		byte[]	msg	= "message digest".getBytes();
		// byte[] userId = "userId".getBytes();
		byte[]	sig	= Sm2Util.signSm3WithSm2(msg, (Sm2PrivateKey) kp.getPri());
		System.out.println(Hex.toHexString(sig));
		System.out.println(Sm2Util.verifySm3WithSm2(msg, sig, (Sm2PublicKey) kp.getPub()));

//        // 由d生成私钥 ---------------------
		BigInteger		d				= new BigInteger(
				"097b5230ef27c7df0fa768289d13ad4e8a96266f0fcb8de40d5942af4293a54a", 16);
		Sm2PrivateKey	bcecPrivateKey	= Sm2Util.sm2PrivateKey(d);
		System.out.println(bcecPrivateKey.getParameters());
		System.out.println(Hex.toHexString(bcecPrivateKey.getEncoded()));
		System.out.println(bcecPrivateKey.getAlgorithm());
		System.out.println(bcecPrivateKey.getFormat());
		System.out.println(bcecPrivateKey.getD());
		System.out.println(bcecPrivateKey instanceof java.security.interfaces.ECPrivateKey);
		System.out.println(bcecPrivateKey instanceof ECPrivateKey);
		System.out.println(bcecPrivateKey.getParameters());

		// 公钥X坐标PublicKeyXHex:
		// 59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4
		// 公钥Y坐标PublicKeyYHex:
		// 9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8
//        PublicKey publicKey = getPublickeyFromX509File(new File("/Users/xxx/Downloads/xxxxx.cer"));
//        System.out.println(publicKey);
		PublicKey publicKey1 = Sm2Util.sm2PublicKey(
				new BigInteger("59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4", 16),
				new BigInteger("9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8", 16));
		System.out.println(publicKey1);
		// System.out.println(publicKey.equals(publicKey1));
		// System.out.println(publicKey.getEncoded().equals(publicKey1.getEncoded()));
//

//        // sm2 encrypt and decrypt test ---------------------
//		KeyPair		kp2			= generateKeyPair();
//		PublicKey	publicKey2	= kp2.getPublic();
//		PrivateKey	privateKey2	= kp2.getPrivate();
//		byte[]		bs			= sm2Encrypt("s".getBytes(), publicKey2);
//		System.out.println(Hex.toHexString(bs));
//		bs = sm2Decrypt(bs, privateKey2);
//		System.out.println(new String(bs));

//        // sm4 encrypt and decrypt test ---------------------
//        //0123456789abcdeffedcba9876543210 + 0123456789abcdeffedcba9876543210 -> 681edf34d206965e86b3e94f536e4246
//        byte[] plain = Hex.decode("0123456789abcdeffedcba98765432100123456789abcdeffedcba98765432100123456789abcdeffedcba9876543210");
//        byte[] key = Hex.decode("0123456789abcdeffedcba9876543210");
//        byte[] cipher = Hex.decode("595298c7c6fd271f0402f804c33d3f66");
//        byte[] bs = sm4Encrypt(key, plain);
//        System.out.println(Hex.toHexString(bs));;
//        bs = sm4Decrypt(key, bs);
//        System.out.println(Hex.toHexString(bs));
	}
}
