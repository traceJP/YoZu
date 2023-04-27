package com.tracejp.yozu.auth.controller;

import com.tracejp.yozu.auth.form.LoginAccountBody;
import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.LoginSmsBody;
import com.tracejp.yozu.auth.form.RegisterBody;
import com.tracejp.yozu.auth.service.SysLoginService;
import com.tracejp.yozu.auth.service.SysRecordLogService;
import com.tracejp.yozu.auth.service.UmsLoginService;
import com.tracejp.yozu.auth.service.UmsRegisterService;
import com.tracejp.yozu.common.core.constant.Constants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.JwtUtils;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.security.auth.AuthUtil;
import com.tracejp.yozu.common.security.service.TokenService;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 *
 * @author yozu
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private UmsLoginService umsLoginService;

    @Autowired
    private UmsRegisterService umsRegisterService;

    @Autowired
    private SysRecordLogService recordLogService;

    /**
     * 系统登录接口
     */
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        LoginUser userInfo = sysLoginService.login(form);
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    /********************************************************************************
     * 注册会员用户登录接口
     ********************************************************************************/
    @PostMapping("login/account")
    public R<?> loginByAccount(@RequestBody LoginAccountBody form) {
        LoginUser memberInfo = umsLoginService.loginByAccount(form);
        // 获取登录token
        return R.ok(tokenService.createToken(memberInfo));
    }

    @PostMapping("login/sms")
    public R<?> loginBySms(@RequestBody LoginSmsBody form) {
        LoginUser memberInfo = umsLoginService.loginBySms(form);
        // 获取登录token
        return R.ok(tokenService.createToken(memberInfo));
    }

    @GetMapping("/login/sms/verify/{phone}")
    public R<?> sendSmsCaptcha(@PathVariable String phone) {
        umsLoginService.sendSmsCaptcha(phone);
        return R.ok();
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            recordLogService.recordLogininfor(username, Constants.LOGOUT, "退出成功");
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        umsRegisterService.register(registerBody);
        return R.ok();
    }

    @GetMapping("register/active")
    public R<?> registerMailActive(@RequestParam("email") String email, @RequestParam("code") String code) {
        umsRegisterService.emailActiveConfirm(email, code);
        return R.ok();
    }

    @GetMapping("register/verify/{email}")
    public R<?> sendRegisterMail(@PathVariable String email) {
        umsRegisterService.sendActiveEmail(email);
        return R.ok();
    }

}
