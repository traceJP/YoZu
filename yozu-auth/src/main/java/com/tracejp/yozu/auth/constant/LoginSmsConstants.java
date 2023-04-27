package com.tracejp.yozu.auth.constant;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/27 21:04
 */
public class LoginSmsConstants {

    /**
     * 短信验证码过期时间（ms）
     */
    public static final long CAPTCHA_EXPIRE = 600000L;

    /**
     * 邮件验证码重发间隔（ms）
     */
    public static final long CAPTCHA_REPEAT_EXPIRE = 6000L;

    /**
     * 短信验证码位数
     */
    public static final int CAPTCHA_COUNT = 6;

}
