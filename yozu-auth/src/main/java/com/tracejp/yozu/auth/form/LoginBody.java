package com.tracejp.yozu.auth.form;

import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import lombok.Data;

/**
 * 用户登录对象
 *
 * @author yozu
 */
@Data
public class LoginBody {
    /**
     * 用户名（System）
     */
    private String username;

    /**
     * 手机号 - 账号
     */
    private String phone;

    /**
     * 邮箱 - 账号
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 短信验证码 | Oauth2.0 code
     */
    private String code;

    /**
     * 登录类型
     */
    private LoginTypeEnum type;

}
