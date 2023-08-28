package com.kivi.crypto.dubbo.mock;

import com.kivi.crypto.dubbo.service.KtfCryptoService;
import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.dto.KtfDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;

public class KtfCryptoServiceMock implements KtfCryptoService {

	@Override
	public String ctx(KtfDTO<byte[]> keyDTO, KtfCryptoAlg alg) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Short crc(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean crc(KtfDTO<String> ctxTokenDTO, Short crc, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] encrypt(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] decrypt(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		return data;
		// throw new KtfMockException();
	}

	@Override
	public byte[] sign(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] sign(KtfDTO<String> ctxTokenDTO, String withId, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean verify(KtfDTO<String> ctxTokenDTO, byte[] data, byte[] sign) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean verify(KtfDTO<String> ctxTokenDTO, String withId, byte[] data, byte[] sign) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] convertPem2Bin(KtfDTO<String> base64KeyDTO, KtfCertType type) {
		throw new KtfMockException();
	}

	@Override
	public String ctx(KtfDTO<KeyPairDO> keypairDTO, AlgSign signAlg) {
		throw new KtfMockException();
	}

	@Override
	public KeyPairDO genKeypair(AlgSign alg) {
		throw new KtfMockException();
	}

}
