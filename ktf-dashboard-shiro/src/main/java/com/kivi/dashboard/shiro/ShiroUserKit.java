package com.kivi.dashboard.shiro;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.jwt.JwtKit;
import com.vip.vjtools.vjkit.collection.ListUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShiroUserKit {

    @Autowired
    private KtfTokenService ktfTokenService;

    @Autowired
    private ShiroUserService shiroUserService;

    public static ShiroUserKit me() {
        return SpringContextHolder.getBean(ShiroUserKit.class);
    }

    /**
     * 将UserVo赋值给shiroUser
     *
     * @param userVo
     * @return
     */
    public ShiroUser userVoToShiroUser(UserVo userVo) {
        if (userVo == null) {
            return null;
        } else {
            ShiroUser su = new ShiroUser();
            su.setId(userVo.getId());
            su.setCifId(userVo.getCifId());
            su.setName(userVo.getName());
            su.setLoginName(userVo.getLoginName());
            su.setUserType(userVo.getUserType());
            su.setStatus(userVo.getStatus());
            List<Long> roleIds = userVo.getRoleIds();
            Set<String> urlSet = CollectionKit.newHashSet();

            if (KtfConstant.SUPER_ADMIN == userVo.getId())
                urlSet = shiroUserService.getPermissions(null);
            else {
                urlSet = shiroUserService.getPermissions(roleIds);
            }

            su.setRoleIds(roleIds);
            su.setUrlSet(urlSet);
            return su;
        }
    }

    /**
     * list去重复
     *
     * @param list
     * @return
     */
    public static List<Long> removeDuplicate(List<Long> list) {
        ListUtil.uniqueNotNullList(list);
        return list;
    }

    /**
     * 验证 accessToken
     * 
     * @param userId
     * @param accessToken
     * @return
     */
    public Boolean verifyAccessToken(String userId, String accessToken) {

        String token = ktfTokenService.cache(userId);
        log.trace("从缓存中获取用户{}的token:{}", userId, token);
        if (token == null) {
            throw new KtfException(KtfError.E_UNAUTHORIZED, "登录状态已过期，请重新登录");
        }

        // 验证 token
        if (!JwtKit.verify(accessToken, token)) {
            log.error("验证JWT token失败");
            throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请重新登录");
        }

        return true;
    }
}
