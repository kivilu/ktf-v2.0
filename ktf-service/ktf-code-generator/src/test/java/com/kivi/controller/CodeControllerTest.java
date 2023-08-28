package com.kivi.controller;

import org.junit.Test;

public class CodeControllerTest {

	@Test
	public void testString() {
		String str = "kivi.dashboard.sys";

		System.out.println(str.substring(str.lastIndexOf('.') + 1));

	}

}
