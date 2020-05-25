package com.kivi.dashboard.sys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import com.kivi.dashboard.shiro.ShiroKit;

class SysUserControllerTest {

	@Test
	void testSavePassword() {
		String	salt		= DigestUtils.md5Hex("12345678");
		String	pwd			= DigestUtils.md5Hex("11111111");
		// String pwd = DigestUtils.md5Hex("22222222");
		String	credential	= ShiroKit.md5(pwd, "system" + salt);

		System.out.println("salt:" + salt);
		System.out.println("pwd:" + pwd);
		System.out.println("credential:" + credential);
	}

	@Test
	void testComparePassword() {
		String	pwd			= "1bbd886460827015e5d605ed44252251";
		String	salt		= "0ae0e16806738d247933dae19d63a0e2";

		String	credential	= ShiroKit.md5(pwd, "admin_biz" + salt);

		System.out.println("salt:" + salt);
		System.out.println("pwd:" + pwd);
		System.out.println("credential:" + credential);

		assertEquals("ad590d2ef508649efbacbc783daa3ea8", credential);
	}

}
