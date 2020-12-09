package com.kivi.dashboard.auth;

import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

public class KtfDefaultAuthentication implements KtfAuthentication {
	private CifCustomerAuthsService authsService;

	@Override
	public boolean auth(LoginForm loginForm, UserVo userVo) {
		userVo.setPassword(loginForm.getPassword());
		return this.authsService.auth(userVo);
	}

	@Override
	public boolean verify(String identifier, String plainData, String signData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthService(CifCustomerAuthsService authsService) {
		this.authsService = authsService;
	}

}
