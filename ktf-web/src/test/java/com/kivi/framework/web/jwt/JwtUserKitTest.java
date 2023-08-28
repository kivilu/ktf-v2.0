package com.kivi.framework.web.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.util.kit.CollectionKit;

public class JwtUserKitTest {

	@BeforeAll
	public void setUp() throws Exception {
	}

	@Test
	public void testAudience() {
		final String	identifier	= "13800100500";
		JwtUserKit		jwtUser		= JwtUserKit.builder().id(1L).identifier(identifier).build();
		String[]		lists		= jwtUser.audience();

		JwtUserDTO		user		= JwtUserKit.audience(CollectionKit.newArrayList(lists));

		assertEquals(Long.valueOf(1L), user.getId());
		assertEquals(identifier, user.getIdentifier());
	}

	@Test
	public void testVerifyJwt() throws Exception {
		final String	identifier	= "13800100500";
		JwtUserKit		jwtUser		= JwtUserKit.builder().id(1L).identifier(identifier).build();

		String			token		= "tokenqerwefs";

		String			jwt			= JwtKit.create(jwtUser, token, null);

		boolean			ret			= JwtKit.verify(jwt, token);
		assertEquals(true, ret);
	}

}
