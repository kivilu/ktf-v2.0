package com.kivi.dashboard.permission.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.org.entity.OrgCorp;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.redis.IRedisService;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.constant.WebConst;
import com.kivi.framework.web.jwt.JwtKit;
import com.kivi.framework.web.jwt.JwtUserKit;
import com.kivi.framework.web.util.kit.HttpKit;
import com.vip.vjtools.vjkit.collection.MapUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description 登录退出Controller
 */
@ConditionalOnProperty(prefix = KtfSwaggerProperties.PREFIX, value = "enable-login-api", havingValue = "true",
    matchIfMissing = true)
@Api(value = "登录退出", tags = {" 登录退出"})
@RestController
@Slf4j
public class LoginController extends DashboardController {

    @Autowired
    private Producer producer;

    @Autowired(required = false)
    private IRedisService redisService;

    @Autowired
    private KtfDashboardProperties ktfDashboardProperties;

    @Autowired
    private KtfTokenService ktfTokenService;

    @KtfTrace("验证码启用状态")
    @GetMapping("/captcha/status")
    public Boolean isKaptcha() {

        return ktfDashboardProperties.getEnableKaptcha();
    }

    @KtfTrace("登录选项")
    @GetMapping("/login/settings")
    public ResultMap loginSettings() {
        Map<String, Object> map = CollectionKit.newHashMap();
        map.put("kaptcha", ktfDashboardProperties.getEnableKaptcha());
        map.put("loginType", ktfDashboardProperties.getLoginType().getCode());

        Map<String, Object> dbMap = sysDicService().getSettings("LOGIN_SETTINGS");
        map.putAll(dbMap);

        return ResultMap.ok().put("data", map);
    }

    @GetMapping("captcha.jpg")
    public void kaptcha(@ApiIgnore HttpServletResponse response, String uuid) {
        log.info("前台请求的UUID:" + uuid);
        if (StringUtils.isBlank(uuid)) {
            throw new RuntimeException("uuid不能为空");
        }
        // 生成文字验证码
        String code = producer.createText();
        redisService.set(uuid, code, expire());

        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setContentType("image/png");

        BufferedImage image = producer.createImage(code);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            log.error("返回客户端图片异常", e);
            throw new KtfException("返回客户端图片异常", e);
        }
    }

    @ApiOperation(value = "获取nonce", notes = "获取nonce")
    @GetMapping("/nonce")
    public ResultMap nonce(@ApiIgnore HttpSession session) {
        String seesionId = session.getId();
        String nonce = UUID.randomUUID().toString();

        log.trace("seesionId：{}，nonce:{}", seesionId, nonce);

        redisService.set(StrKit.join("nonce-", seesionId), nonce, 10);

        return ResultMap.ok().put("data", nonce);
    }

    /**
     * 登录
     * 
     * @throws Exception
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    // @KtfTrace("login")
    public ResultMap login(@Valid @RequestBody LoginForm form, @ApiIgnore HttpSession session) throws Exception {
        log.info("login请求登录");

        String seesionId = session.getId();
        String nonce = (String)redisService.get(StrKit.join("nonce-", seesionId));

        log.trace("seesionId：{}，nonce:{}", seesionId, nonce);

        if (ktfDashboardProperties.getEnableKaptcha()) {
            String validateCode = (String)redisService.get(form.getUuid());
            log.info("session中的图形码字符串:{}", validateCode);

            // 比对
            if (StringUtils.isBlank(validateCode) || StringUtils.isBlank(form.getCaptcha())
                || !StringUtils.equalsIgnoreCase(validateCode, form.getCaptcha())) {
                return ResultMap.error("验证码错误");
            }
        }

        UserVo userVo = sysUserService().getUserVo(form.getUserName());
        if (null == userVo) {
            log.error("账号{}不存在", form.getUserName());
            return ResultMap.error(KtfError.E_UNAUTHORIZED, "登录验证未通过");
        }

        if (StrKit.isNotBlank(form.getUuid())) {
            String newUuid = form.getUuid() + nonce;
            form.setUuid(newUuid);
            log.info("客户端请求uuid：{}，用于验证签名的组合uuid：{}.", form.getUuid(), newUuid);
        }

        Integer authType = customerAuthsService().auth(form, userVo);
        userVo.setAuthType(authType);

        // 当企业不存在或者企业被禁用不允许登录
        if (userVo.getUserType() != UserType.SYS.value) {
            OrgCorp orgCorp = orgCorpService().getById(userVo.getOrgId());
            if (null != orgCorp && orgCorp.getStatus() == KtfStatus.DISABLED.code) {
                return ResultMap.error("企业被禁用，该账户不允许登录");
            } else if (null == orgCorp) {
                return ResultMap.error("企业不存在，该账户不允许登录");
            }
        }

        if (ktfDashboardProperties.getEnableKaptcha()) {
            // 清除验证码
            redisService.del(form.getUuid());
        }

        // 判断用户状态
        if (KtfStatus.LOCKED.code == userVo.getStatus()) {
            // 用户已被禁用
            return ResultMap.error(KtfError.E_LOCKED, "用户已被禁用");
        }

        // 生成token，并保存到数据库
        ResultMap result = createToken(userVo);

        // 保存登录时间和IP
        CifCustomerAuths customerAuths = new CifCustomerAuths();
        customerAuths.setId(userVo.getId());
        customerAuths.setLastIp(HttpKit.getRemoteAddress());
        customerAuths.setLastTime(LocalDateTime.now());
        CompletableFuture.runAsync(new SaveRunnable(customerAuths));

        return result;
    }

    /**
     * 退出
     */
    @ApiOperation(value = "退出", notes = "退出")
    @PostMapping("/logout")
    public ResultMap logout() throws Exception {
        // 生成一个token
        ShiroUser shiroUser = ShiroKit.getUser();
        ktfTokenService.evictJwt(shiroUser.getId().toString());
        return ResultMap.ok();
    }

    public ResultMap createToken(UserVo userVo) throws Exception {

        // 从缓存中获取
        // 生成一个token
        String token =
            ktfTokenService.token(userVo.getId(), userVo.getCifId(), userVo.getUserType(), userVo.getLoginMode());

        // 过期时间

        JwtUserKit jwtUser = JwtUserKit.builder().id(userVo.getId()).identifier(userVo.getLoginName())
            .userType(userVo.getUserType()).authType(userVo.getAuthType()).build();

        // 创建Jwt Toen
        String jwtToken = null;
        if (userVo.getUserType().intValue() == UserType.SRV.value) {
            jwtToken = JwtKit.create(jwtUser, token, null);
            ktfTokenService.cacheJwt(jwtUser.getId().toString(), token, jwtToken, -1);
        } else {
            DateTime now = DateTime.now();
            Date expireTime = now.plusSeconds(expire()).toDate();
            jwtToken = JwtKit.create(jwtUser, token, expireTime);
            ktfTokenService.cacheJwt(jwtUser.getId().toString(), token, jwtToken, expire());
        }

        ResultMap r =
            KtfStatus.ENABLED.code == userVo.getStatus() ? ResultMap.ok() : ResultMap.error(KtfError.ACCEPTED, "首次访问");

        Map<String, Object> map = MapUtil.newHashMap("token", jwtToken);
        map.put(WebConst.AUTH_TOKEN_EXPIRE, expire());
        r.data(map);
        return r;
    }

    private int expire() {
        return ktfDashboardProperties.getSession().getExpireTime();
    }

    class SaveRunnable implements Runnable {
        private final CifCustomerAuths customerAuths;

        public SaveRunnable(CifCustomerAuths customerAuths) {
            this.customerAuths = customerAuths;
        }

        @Override
        public void run() {
            customerAuthsService().updateById(customerAuths);

        }
    }
}
