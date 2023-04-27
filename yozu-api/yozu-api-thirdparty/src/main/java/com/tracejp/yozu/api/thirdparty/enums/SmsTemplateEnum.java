package com.tracejp.yozu.api.thirdparty.enums;

/**
 * <p> 短信模板 <p/>
 *
 * @author traceJP
 * @since 2023/4/27 21:36
 */
public enum SmsTemplateEnum {

    LOGIN_CAPTCHA_TEMPLATE((short)1, "SMS_154950909");

    /**
     * 枚举编号
     */
    private final Short code;

    /**
     * 模板编号
     */
    private final String templateId;

    SmsTemplateEnum(Short code, String templateId) {
        this.code = code;
        this.templateId = templateId;
    }

    public Short getCode() {
        return code;
    }

    public String getTemplateId() {
        return templateId;
    }

}
