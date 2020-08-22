package com.kivi.dashboard.auth;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.form.LoginForm;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KtfSimpleAuthentication implements KtfAuthentication {

	@KtfTrace("默认用户密码认证")
	@Override
	public boolean auth(LoginForm form, UserVo userVo) {
		String actualPass = ShiroKit.md5(form.getPassword(), userVo.getLoginName() + userVo.getSalt());
		log.trace("期望密码：{}，实际密码：{}", userVo.getPassword(), actualPass);
		return userVo.getPassword().equals(actualPass);
	}

	@Override
	public boolean verify(String userName, String plainData, String signData) {
		return true;
	}

}
