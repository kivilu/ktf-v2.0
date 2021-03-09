package com.kivi.cif.auth;

import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

/**
 * 用户登录验证
 * 
 * @author xueqi
 *
 */
public interface CifAuthentication {

	/**
	 * 用户验证
	 * 
	 * @param form    登录Form
	 * @param cifAuth 客户auth对象
	 * @return true—验证通过， false—验证未通过
	 */
	Integer auth(LoginForm form, UserVo userVo);

	/**
	 * 生成认证凭据
	 * 
	 * @param credential
	 * @param salt
	 * @return
	 */
	String credential(String credential, String salt);

	/**
	 * 验证用户标识签名
	 * 
	 * @param identifier
	 * @param plainData
	 * @param signData
	 * @return
	 */
	boolean verify(String identifier, String plainData, String signData);
}
