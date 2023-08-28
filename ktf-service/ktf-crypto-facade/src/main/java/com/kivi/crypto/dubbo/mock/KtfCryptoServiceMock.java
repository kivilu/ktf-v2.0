package com.kivi.crypto.dubbo.mock;

import com.kivi.crypto.dubbo.service.KtfCryptoService;
import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;

public class KtfCryptoServiceMock implements KtfCryptoService {

	@Override
	public String ctx(WarpReqDTO<byte[]> keyDTO, KtfCryptoAlg alg) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Short crc(WarpReqDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean crc(WarpReqDTO<String> ctxTokenDTO, Short crc, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] encrypt(WarpReqDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] decrypt(WarpReqDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		return data;
		//throw new KtfMockException();
	}

	@Override
	public byte[] sign(WarpReqDTO<String> ctxTokenDTO, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] sign(WarpReqDTO<String> ctxTokenDTO, String withId, byte[] data) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean verify(WarpReqDTO<String> ctxTokenDTO, byte[] data, byte[] sign) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean verify(WarpReqDTO<String> ctxTokenDTO, String withId, byte[] data, byte[] sign) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public byte[] convertPem2Bin(WarpReqDTO<String> base64KeyDTO, KtfCertType type) {
		throw new KtfMockException();
	}

	@Override
	public String ctx(WarpReqDTO<KeyPairDO> keypairDTO, AlgSign signAlg) {
		throw new KtfMockException();
	}

	@Override
	public KeyPairDO genKeypair(AlgSign alg) {
		throw new KtfMockException();
	}

}
