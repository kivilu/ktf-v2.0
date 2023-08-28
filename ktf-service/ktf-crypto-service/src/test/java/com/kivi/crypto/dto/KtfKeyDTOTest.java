package com.kivi.crypto.dto;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class KtfKeyDTOTest {

	@Test
	public void test() {
		ParserConfig.getGlobalInstance().addAccept("com.kivi.,com.ins.");
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
		String	json	= "{\r\n" + "    \"@type\": \"com.kivi.crypto.dto.KtfKeyDTO\",\r\n" + "    \"key\": {\r\n"
				+ "        \"@type\": \"com.kivi.framework.crypto.domain.KeyPairDO\",\r\n"
				+ "        \"pri_pem\": \"-----BEGIN EC PRIVATE KEY-----\\r\\nMHcCAQEEILidz2rd+1HiqbU7sigYnP2pc++kgvtXwkUvsn3C1QdqoAoGCCqBHM9V\\r\\nAYItoUQDQgAEMaSfDpa5Vq1kvS7QJoRfaN/FL+qG35u1Dx2NeU5VJStiCrwY8Jb2\\r\\noQ4GDGDTEyqBe5fo8JPVdvt8UVGMK6OJqw==\\r\\n-----END EC PRIVATE KEY-----\\r\\n\",\r\n"
				+ "        \"pub_pem\": \"-----BEGIN PUBLIC KEY-----\\r\\nMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEMaSfDpa5Vq1kvS7QJoRfaN/FL+qG\\r\\n35u1Dx2NeU5VJStiCrwY8Jb2oQ4GDGDTEyqBe5fo8JPVdvt8UVGMK6OJqw==\\r\\n-----END PUBLIC KEY-----\\r\\n\"\r\n"
				+ "    },\r\n" + "    \"signAlg\": \"SM2_SM3\",\r\n" + "	\"cryptoAlg\": \"ddd\",\r\n"
				+ "	\"iv\": null\r\n" + "}";

		Object	obj		= JSON.parseObject(json, Object.class);

		System.out.println(obj);
	}

}
