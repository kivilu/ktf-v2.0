package com.kivi.sys.shiro;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.UnauthenticatedException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.jwt.JwtKit;
import com.kivi.framework.web.jwt.JwtUserKit;
import com.kivi.shiro.service.SysKit;
import com.vip.vjtools.vjkit.collection.ListUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShiroUserKit {

    @Autowired
    private KtfTokenService ktfTokenService;

    @Autowired
    private KtfSysProperties ktfDashboardProperties;

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
            su.setIpkDomain(userVo.getIpkDomain());
            List<Long> roleIds = userVo.getRoleIds();
            Set<String> urlSet = CollectionKit.newHashSet();

            if (KtfConstant.SUPER_ADMIN == userVo.getId())
                urlSet = SysKit.me().getPermissions(null);
            else {
                urlSet = SysKit.me().getPermissions(roleIds);
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

    public String generateJwtToken(UserVo userVo) {
        // 从缓存中获取
        // 生成一个token
        String token =
            ktfTokenService.token(userVo.getId(), userVo.getCifId(), userVo.getUserType(), userVo.getLoginMode());

        // 过期时间

        JwtUserKit jwtUser = JwtUserKit.builder().id(userVo.getId()).identifier(userVo.getLoginName())
            .userType(userVo.getUserType()).authType(userVo.getAuthType()).build();

        // 创建Jwt Toen
        String jwtToken = null;
        try {
            if (userVo.getUserType().intValue() == UserType.SRV.value) {
                jwtToken = JwtKit.create(jwtUser, token);
                ktfTokenService.cacheJwt(jwtUser.getId().toString(), token, jwtToken, -1);
            } else {
                DateTime now = DateTime.now();
                Date expireTime = now.plusSeconds(expire()).toDate();
                jwtToken = JwtKit.create(jwtUser, token, expireTime);
                ktfTokenService.cacheJwt(jwtUser.getId().toString(), token, jwtToken, expire());
            }
        } catch (Exception e) {
            log.error("生成JWT token异常", e);
            throw new KtfException("生成JWT token异常");
        }

        return jwtToken;
    }

    public void evicJwtToken(Long userId) {
        if (userId == null)
            return;

        ktfTokenService.evict(userId.toString());
        ktfTokenService.evictJwt(userId.toString());
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
            throw new UnauthenticatedException("登录状态已过期，请重新登录");
        }

        // 验证 token
        if (!JwtKit.verify(accessToken, token)) {
            log.error("验证JWT token失败");
            throw new UnauthenticatedException("用户尚未登录，请重新登录");
        }

        return true;
    }

    public Integer getTokenRefreshInterval() {
        return ktfDashboardProperties.getSession().getExpireTime();
    }

    private int expire() {
        return ktfDashboardProperties.getSession().getExpireTime();
    }
}
