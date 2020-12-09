package com.kivi.dashboard.auth;

import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

/**
 * 用户登录验证
 * 
 * @author xueqi
 *
 */
public interface KtfAuthentication {

	void setAuthService(CifCustomerAuthsService authsService);

	/**
	 * 用户验证
	 * 
	 * @param loginForm 请求请form
	 * @param userVo    用户信息
	 * @return true—验证通过， false—验证未通过
	 */
	boolean auth(LoginForm loginForm, UserVo userVo);

	/**
	 * 验证签名
	 * 
	 * @param identifier 用户标识
	 * @param plainData  签名原文
	 * @param signData   签名
	 * @return
	 */
	boolean verify(String identifier, String plainData, String signData);
}
