package com.kivi.crypto.service.impl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.kivi.crypto.service.SymmCryptoService;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.util.kit.ByteStringKit;

public abstract class BaseSymmCryptoService implements SymmCryptoService {

	protected static final Charset						CHARSET	= Charset.forName("UTF-8");

	protected static ConcurrentHashMap<String, SymCtx>	ctxMap	= new ConcurrentHashMap<>();

	protected String uuid(String algName, byte[] key, byte[] iv) {
		int			size	= 4 + algName.length() + key.length + (iv != null ? iv.length : 0);
		ByteBuffer	buff	= ByteBuffer.allocate(size);
		buff.put(algName.getBytes(Charset.forName("UTF-8"))).put(key);
		if (iv != null)
			buff.put(iv);

		return DigestUtils.md5Hex(buff.array());
	}

	protected void putCtx(SymCtx ctx) {
		ctxMap.put(ctx.getUuid(), ctx);
	}

	protected SymCtx getCtx(String uuid) {
		return ctxMap.get(uuid);
	}

	/**
	 * 补0填充
	 * 
	 * @param blockSize
	 * @param data
	 * @return
	 */
	protected byte[] zeroPadding(int blockSize, byte[] data) {
		int			padLen	= blockSize - data.length % blockSize;

		ByteBuffer	padData	= ByteBuffer.allocate(data.length + padLen);
		padData.put(data);

		return padData.array();
	}

	protected byte[] trimZeroPadding(int blockSize, byte[] data) {
		int padLen = 0;
		for (padLen = 0; padLen < blockSize; padLen++) {
			if (data[data.length - 1 - padLen] != 0x0)
				break;
		}

		int			size	= data.length - padLen;
		ByteBuffer	padData	= ByteBuffer.allocate(size);
		padData.put(data, 0, size);
		return padData.array();
	}

	@Override
	public String encryptToBase64(String ctxToken, byte[] data) {
		byte[] encData = encrypt(ctxToken, data);

		return ByteStringKit.toString(encData, ByteStringKit.BASE64);
	}

	@Override
	public byte[] decryptFromBase64(String ctxToken, String dataBase64) {
		byte[] plain = decrypt(ctxToken, ByteStringKit.toBytes(dataBase64, ByteStringKit.BASE64));

		return plain;
	}

	@Override
	public String encryptToHex(String ctxToken, byte[] data) {
		byte[] encData = encrypt(ctxToken, data);
		return ByteStringKit.toString(encData, ByteStringKit.HEX);
	}

	@Override
	public byte[] decryptFromHex(String ctxToken, String data) {
		byte[] plain = decrypt(ctxToken, ByteStringKit.toBytes(data, ByteStringKit.HEX));
		return plain;
	}

	@Override
	public byte[] key(String ctx) {
		SymCtx symCtx = ctxMap.get(ctx);
		if (symCtx == null)
			return null;

		return symCtx.getKey();
	}

	@Override
	public void evictCtx(String ctxToken) {
		ctxMap.remove(ctxToken);
	}

	@Override
	public abstract byte[] encrypt(String ctxToken, byte[] data);

	@Override
	public abstract byte[] decrypt(String ctxToken, byte[] data);

}
