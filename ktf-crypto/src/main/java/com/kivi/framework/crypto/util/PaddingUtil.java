package com.kivi.framework.crypto.util;

import java.nio.ByteBuffer;

public class PaddingUtil {

	/**
	 * 补0填充
	 * 
	 * @param blockSize
	 * @param data
	 * @return
	 */
	public static byte[] zeroPadding(int blockSize, byte[] data) {
		int			padLen	= blockSize - data.length % blockSize;

		ByteBuffer	padData	= ByteBuffer.allocate(data.length + padLen);
		padData.put(data);

		return padData.array();
	}

	/**
	 * 去掉填充的0
	 * 
	 * @param blockSize
	 * @param data
	 * @return
	 */
	public static byte[] trimZeroPadding(int blockSize, byte[] data) {
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

}
