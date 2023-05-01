package com.tracejp.yozu.member.api.enums;

/**
 * <p> 社交平台枚举 <p/>
 *
 * @author traceJP
 * @since 2023/4/28 8:34
 */
public enum SocialTypeEnum {

    /**
     * QQ
     */
    QQ("0", "QQ平台"),

    /**
     * 微博
     */
    WEIBO("1", "微博平台");

    /**
     * 枚举编号 参考 UmsMemberOauth.socialType
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    SocialTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
