package com.tracejp.yozu.auth.form.constant;

/**
 * <p> 登录方式枚举 <p/>
 *
 * @author traceJP
 * @since 2023/4/19 8:46
 */
public enum LoginTypeEnum {

    SYSTEM_USER((short) 1, "系统用户登录"),

    MEMBER_USER_EMAIL((short) 2, "注册会员用户邮箱登录"),

    MEMBER_USER_PHONE((short) 3, "注册会员用户手机登录"),

    MEMBER_USER_OAUTH2_WEIBO((short) 4, "注册会员用户社交微博登录");


    private Short code;

    private String desc;

    LoginTypeEnum(Short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
