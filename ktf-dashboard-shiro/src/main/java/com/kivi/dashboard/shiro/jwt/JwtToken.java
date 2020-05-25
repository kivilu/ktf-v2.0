package com.kivi.dashboard.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description OAuth2Token
 */
public class JwtToken implements AuthenticationToken {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	private final Long			userId;
	private final String		token;

	public JwtToken(Long userId, String token) {
		this.userId	= userId;
		this.token	= token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	public Long getUserId() {
		return userId;
	}
}
