package com.tracejp.yozu.common.core.constant;

/**
 * <p> 验证码相关常量 <p/>
 *
 * @author traceJP
 * @since 2023/5/10 17:11
 */
public class CaptchaConstants {

    /**
     * 注册邮箱激活码 redis key
     */
    public static final String AUTH_EMAIL_ACTIVE_CODE_KEY = "auth_email_code:";

    /**
     * 短信验证码 redis key
     */
    public static final String AUTH_SMS_CAPTCHA_KEY = "auth_sms_captcha:";

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

    /**
     * 激活链接过期时间（ms）
     */
    public static final long ACTIVE_CODE_EXPIRE = 1800000L;

    /**
     * 邮件验证码重发间隔（ms）
     */
    public static final long CODE_REPEAT_EXPIRE = 60000L;

}
