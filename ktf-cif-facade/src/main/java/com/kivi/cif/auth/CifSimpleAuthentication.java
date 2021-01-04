package com.kivi.cif.auth;

import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.cif.properties.CifProperties;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.util.DigestUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CifSimpleAuthentication implements CifAuthentication {

    @Autowired
    private CifProperties cifProperties;

    @KtfTrace("默认用户密码认证")
    @Override
    public Integer auth(LoginForm form, UserVo userVo) {
        String authCredential = this.credential(form.getPassword(), userVo.getSalt());
        if (!StrKit.equalsNotNull(authCredential, userVo.getPassword())) {
            log.trace("用户{}密码期望值：{}，实际值：{}", form.getUserName(), userVo.getPassword(), authCredential);
            log.error("用户{}密码认证不通过", form.getUserName());
            throw new KtfException(KtfError.E_UNAUTHORIZED, "登录验证未通过");
        }

        return form.getType() == null ? KtfConstant.DEFAUT_AUTH_TYPE : form.getType();
    }

    @Override
    public String credential(String credential, String salt) {
        if (StrKit.isBlank(salt))
            throw new KtfException("密码salt为null");
        log.trace("默认密码摘要算法：{}\nsalt：{}\ncredential：{}", cifProperties.getAlgDigest(), salt, credential);
        return DigestUtil.hashHex(cifProperties.getAlgDigest(), StrKit.join(credential, salt));
    }

}
