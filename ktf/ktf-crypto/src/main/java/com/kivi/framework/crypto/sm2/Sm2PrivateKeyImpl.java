package com.kivi.framework.crypto.sm2;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.spec.ECParameterSpec;

import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;

public class Sm2PrivateKeyImpl extends BCECPrivateKey implements Sm2PrivateKey {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private ECPrivateKeyParameters	ecPrivateKeyParameters;
	private byte[]					withId				= StrKit.toBytes("1234567812345678");

	public Sm2PrivateKeyImpl(BCECPrivateKey privateKey) {
		super(Sm2KeyPairImpl.ALGO_NAME_EC, privateKey);
		this.ecPrivateKeyParameters = new ECPrivateKeyParameters(this.getD(), Sm2KeyPairImpl.DOMAIN_PARAMS);
	}

	public Sm2PrivateKeyImpl(String algoNameEc, ECPrivateKeyParameters priKey, Sm2PublicKeyImpl publicKey,
			ECParameterSpec spec, ProviderConfiguration configuration) {
		super(algoNameEc, priKey, publicKey, spec, configuration);
		this.ecPrivateKeyParameters = priKey;
	}

	@Override
	public ECPrivateKeyParameters getPrivateKeyParameters() {
		return ecPrivateKeyParameters;
	}

	@Override
	public byte[] getWithId() {
		return withId;
	}

	@Override
	public void setWithId(byte[] withId) {
		this.withId = withId;
	}

	public BigInteger getD() {
		return super.getD();
	}

	@Override
	public byte[] getData() {
		byte[] result = null;
		try {
			ASN1Sequence	seq				= ASN1Sequence.getInstance(this.getEncoded());

			ASN1Encodable	asn1Encodable	= seq.getObjectAt(2);
			DEROctetString	eEROctetString	= (DEROctetString) asn1Encodable;

			DLSequence		dLSequence		= (DLSequence) ASN1Sequence.fromByteArray(eEROctetString.getOctets());
			asn1Encodable	= dLSequence.getObjectAt(1);
			eEROctetString	= (DEROctetString) asn1Encodable;
			result			= eEROctetString.getOctets();
		} catch (IOException e) {
			throw new KtfException(e);
		}
		return result;

	}
}
