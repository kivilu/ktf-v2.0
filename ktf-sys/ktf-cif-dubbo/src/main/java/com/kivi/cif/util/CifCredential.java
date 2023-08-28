package com.kivi.cif.util;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.kivi.framework.util.kit.CollectionKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CifCredential {

	private List<Object> datas;

	private CifCredential() {
		datas = CollectionKit.newArrayList();
	}

	public static CifCredential builder() {
		return new CifCredential();
	}

	public CifCredential add(Object data) {
		if (data != null)
			datas.add(data);

		return this;
	}

	public String build() {
		StringBuilder plain = new StringBuilder();

		datas.forEach(data -> plain.append(data));

		if (log.isTraceEnabled())
			log.trace("计算Credential的原文：{}", plain.toString());

		return DigestUtils.md5Hex(plain.toString());
	}
}
