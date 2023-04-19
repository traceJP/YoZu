package com.tracejp.yozu.auth.controller;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.RegisterBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.auth.handler.LoginHandlerContext;
import com.tracejp.yozu.auth.service.SysRecordLogService;
import com.tracejp.yozu.common.core.constant.Constants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.utils.JwtUtils;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.security.auth.AuthUtil;
import com.tracejp.yozu.common.security.service.TokenService;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import com.tracejp.yozu.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 * 
 * @author yozu
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    LoginHandlerContext loginService;

    @Autowired
    private SysRecordLogService recordLogService;

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginTypeEnum loginType = form.getType();
        LoginUser userInfo = loginService.login(form, loginType);
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            recordLogService.recordLogininfor(username, Constants.LOGOUT, "退出成功");
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册 单独抽离一个类出来注册
        // sysLoginService.register(registerBody);
        return R.ok();
    }
}
