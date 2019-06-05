package com.kivi.framework.crypto.util;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.util.StringUtils;

import com.kivi.framework.crypto.domain.KeyPairDO;

public class KeyPairResult extends KeyPairDO {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private PublicKey			pub;
	private PrivateKey			pri;

	public PublicKey getPub() {
		return pub;
	}

	public void setPub(PublicKey pub) {
		this.pub = pub;
	}

	public PrivateKey getPri() {
		return pri;
	}

	public void setPri(PrivateKey pri) {
		this.pri = pri;
	}

	@Override
	public String getPub_pem() {
		if (StringUtils.isEmpty(pub_pem)) {
			pub_pem = KeyPairUtil.convertPublicKeyToPemString(pub);
		}
		return pub_pem;
	}

	@Override
	public void setPub_pem(String pub_pem) {
		this.pub_pem = pub_pem;
	}

	@Override
	public String getPri_pem() {
		if (StringUtils.isEmpty(pri_pem)) {
			pri_pem = KeyPairUtil.convertPrivateKeyToPemString(pri);
		}
		return pri_pem;
	}

	@Override
	public void setPri_pem(String pri_pem) {
		this.pri_pem = pri_pem;
	}

}
