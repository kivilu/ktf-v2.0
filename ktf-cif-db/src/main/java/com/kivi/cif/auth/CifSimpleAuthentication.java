package com.kivi.cif.auth;

import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.cif.properties.CifProperties;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.crypto.util.DigestUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CifSimpleAuthentication implements CifAuthentication {

	@Autowired
	private CifProperties cifProperties;

	@KtfTrace("默认用户密码认证")
	@Override
	public boolean verifyPass(String password, String salt, String credential) {
		String authCredential = this.credential(password, salt);
		return StrKit.equalsNotNull(authCredential, credential);
	}

	@Override
	public String credential(String credential, String salt) {
		if (StrKit.isBlank(salt))
			throw new KtfException("密码salt为null");
		log.trace("默认密码摘要算法：{}\nsalt：{}\ncredential：{}", cifProperties.getAlgDigest(), salt, credential);
		return DigestUtil.hashHex(cifProperties.getAlgDigest(), StrKit.join(credential, salt));
	}

	@Override
	public boolean verifySign(String identifier, String plainData, String signData) {
		// TODO Auto-generated method stub
		return false;
	}

}
