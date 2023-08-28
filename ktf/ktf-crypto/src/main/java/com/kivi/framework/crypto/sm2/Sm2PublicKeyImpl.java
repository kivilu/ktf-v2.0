package com.kivi.framework.crypto.sm2;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.spec.ECParameterSpec;

import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;

public class Sm2PublicKeyImpl extends BCECPublicKey implements Sm2PublicKey {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private ECPublicKeyParameters	ecPublicKeyParameters;
	private byte[]					withId				= StrKit.toBytes("1234567812345678");

	public Sm2PublicKeyImpl(BCECPublicKey publicKey) {
		super(Sm2KeyPairImpl.ALGO_NAME_EC, publicKey);
		this.ecPublicKeyParameters = new ECPublicKeyParameters(this.getQ(), Sm2KeyPairImpl.DOMAIN_PARAMS);
	}

	public Sm2PublicKeyImpl(String algoNameEc, ECPublicKeyParameters pubKey, ECParameterSpec spec,
			ProviderConfiguration configuration) {
		super(algoNameEc, pubKey, spec, configuration);
		this.ecPublicKeyParameters = pubKey;
	}

	@Override
	public ECPublicKeyParameters getPublicKeyParameters() {
		return ecPublicKeyParameters;
	}

	@Override
	public byte[] getWithId() {
		return withId;
	}

	@Override
	public void setWithId(byte[] withId) {
		this.withId = withId;
	}

	public byte[] getData() {
		byte[] ecP = new byte[64];

		try {
			SubjectPublicKeyInfo	subjectPublicKeyInfo	= SubjectPublicKeyInfo.getInstance(this.getEncoded());
			ASN1BitString			publicKeyData			= subjectPublicKeyInfo.getPublicKeyData();
			byte[]					publicKey				= publicKeyData.getEncoded();
			byte[]					encodedPublicKey		= publicKey;

			System.arraycopy(encodedPublicKey, 4, ecP, 0, ecP.length);

			byte[]	certPKX	= new byte[32];
			byte[]	certPKY	= new byte[32];
			System.arraycopy(ecP, 0, certPKX, 0, 32);
			System.arraycopy(ecP, 32, certPKY, 0, 32);

		} catch (IOException e) {
			throw new KtfException(e);
		}

		return ecP;
	}

}
