package com.kivi.cif.auth;

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
	 * @param password   待验证的用户密码
	 * @param salt       密码盐
	 * @param credential 期望值
	 * @return true—验证通过， false—验证未通过
	 */
	boolean verifyPass(String password, String salt, String credential);

	/**
	 * 验证签名
	 * 
	 * @param identifier 用户标识
	 * @param plainData  签名原文
	 * @param signData   签名
	 * @return
	 */
	boolean verifySign(String identifier, String plainData, String signData);

	/**
	 * 生成认证凭据
	 * 
	 * @param credential
	 * @param salt
	 * @return
	 */
	String credential(String credential, String salt);

}
