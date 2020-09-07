package com.kivi.framework.crypto.sm2;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import com.kivi.framework.util.kit.ByteStringKit;

class Sm2UtilTest {

	// private static final String priKey =
	// "37F81121D470A727F2FCBD4C688E54CD8A673DC8F5E47451212A278AD64BD632";
	// private static final String pubKey =
	// "A7245459ACA827B736C7BD34B36F7253EA8F38392C000D08E42529FD1AF224163B4991D4B6A667C68FF20D53DF070F6FE8BD478BD3EE9AB301F0215FBF6F6DF6";
	private static final String	priKey	= "C4B3691BB8011AA646587397BA78005E9E774ECF248DBFDBDCEFB6BACA70D80F";
	private static final String	pubKey	= "745F982E46AAD5B22515D0E63A70B361B83490AB66CB24AA2BF5D301917F9F8290D14A845FEEEE38036DF160AD64E2C1BB615DD40A11B1A7F76AD94AB710C126";

	private static final byte[]	msg		= ByteStringKit
			.fromHex("A4DF35A19A20F846A8BCA24E224FD6B8D72D7466B74A02E5C3567D9C6D2C10E8");
//	private static final byte[]	sign	= ByteStringKit.fromHex(
//			"532072E86449EA9FC61CC023220734D1BA61A46460C678606E5F4BC9DB1DFA9CFC4AE86687E049F95303CBF3C71B9F0DCAF3068BFF62296E020A7215114C9204");
	private static final byte[]	sign	= ByteStringKit.fromHex(
			"9973ECE7A5439C3C9C90CE5D8370ABE7C4AE450DD8AE217D60DD0C4DEDAC40F5B0774946D8EF307EAA1ACEF7E87DC9851316DF784C03DE23681BFFAE66262B25");

	@Test
	void testSm2PublicKeyXY() {
		BigInteger	x	= new BigInteger("A7245459ACA827B736C7BD34B36F7253EA8F38392C000D08E42529FD1AF22416", 16);
		BigInteger	y	= new BigInteger("3B4991D4B6A667C68FF20D53DF070F6FE8BD478BD3EE9AB301F0215FBF6F6DF6", 16);

		System.out.println(x.toString(16));
		Sm2PublicKey publicKey = Sm2Util.sm2PublicKey(x, y);
		System.out.println(publicKey);

	}

	@Test
	void testSm2SignWithSm3() throws NoSuchAlgorithmException {
		Sm2PublicKey	publicKey	= Sm2Util.sm2PublicKeyXY(pubKey);
		Sm2PrivateKey	privateKey	= Sm2Util.sm2PrivateKeyD(priKey);

		byte[]			sig			= Sm2Util.signSm3WithSm2(msg, privateKey);
		boolean			ret			= Sm2Util.verifySm3WithSm2(msg, sig, publicKey);

		assertEquals(true, ret);
	}

	@Test
	void testSm2Verify() {
		Sm2PublicKey	publicKey	= Sm2Util.sm2PublicKeyXY(pubKey);

		String			message		= "sfske832efhewiuwu382jjakkio32i89eujdsajkqeuw8j";

		publicKey.setWithId("sm2_selftest".getBytes());
		boolean ret = Sm2Util.verifySm3WithSm2(message.getBytes(), sign, publicKey);

		assertEquals(true, ret);
	}

	@Test
	void testSm2SignWithNoHash() throws NoSuchAlgorithmException {
		Sm2PublicKey		publicKey	= Sm2Util.sm2PublicKeyXY(pubKey);
		Sm2PrivateKey		privateKey	= Sm2Util.sm2PrivateKeyD(priKey);

		SM2Signer			signer		= new SM2Signer();
		SM2Signer.Signature	sig			= signer.signNoHash(msg, privateKey.getD());
		boolean				ret			= signer.verifyNoHash(msg, sig, publicKey.getQ());

		assertEquals(true, ret);
//
		byte[] sigdata = Sm2Util.signWithNoHash(msg, privateKey);

		ret = Sm2Util.verifyWithNoHash(msg, sigdata, publicKey);

		assertEquals(true, ret);

	}

	@Test
	void testSm2VerifyWithNoHash() throws NoSuchAlgorithmException {
		Sm2PublicKey		publicKey	= Sm2Util.sm2PublicKeyXY(pubKey);

		BigInteger			r			= new BigInteger(
				"532072E86449EA9FC61CC023220734D1BA61A46460C678606E5F4BC9DB1DFA9C", 16);
		BigInteger			s			= new BigInteger(
				"FC4AE86687E049F95303CBF3C71B9F0DCAF3068BFF62296E020A7215114C9204", 16);
		SM2Signer.Signature	sig			= new SM2Signer.Signature(r, s);
		SM2Signer			signer		= new SM2Signer();
		boolean				ret			= signer.verifyNoHash(msg, sig, publicKey.getQ());
		assertEquals(true, ret);

		ret = Sm2Util.verifyWithNoHash(msg, sign, publicKey);

		assertEquals(true, ret);

	}

}
