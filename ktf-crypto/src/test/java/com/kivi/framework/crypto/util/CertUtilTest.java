package com.kivi.framework.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.jupiter.api.Test;

import com.vip.vjtools.vjkit.io.FileUtil;

class CertUtilTest {

	@Test
	void testReadX509Cert() {
		String fullpath = "/tmp/inssaas.cer";
		try (InputStream in = FileUtil.asInputStream(fullpath)) {
			// 创建x.509工厂类
			CertificateFactory	cf		= CertificateFactory.getInstance("X.509");
			// 创建证书实例
			X509Certificate		cert	= (X509Certificate) cf.generateCertificate(in);

			System.out.println(cert.getSubjectDN().getName());

		} catch (IOException | CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
