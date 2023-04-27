package com.tracejp.yozu.auth.constant;

/**
 * <p> 邮箱登录常量 <p/>
 *
 * @author traceJP
 * @since 2023/4/26 17:17
 */
public class LoginEmailConstants {

    /**
     * 激活链接过期时间（ms）
     */
    public static final Long EMAIL_ACTIVE_CODE_EXPIRE = 1800000L;

    /**
     * 邮件验证码重发间隔（ms）
     */
    public static final Long EMAIL_CODE_REPEAT_EXPIRE = 60000L;

}
