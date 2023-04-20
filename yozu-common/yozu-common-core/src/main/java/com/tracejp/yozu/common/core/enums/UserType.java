package com.tracejp.yozu.common.core.enums;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/20 18:44
 */
public enum UserType {

    SYSTEM_USER((short)1, "系统用户"),

    MEMBER_USER((short)2, "注册会员用户");

    private Short code;

    private String desc;

    UserType(Short code, String desc) {
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
