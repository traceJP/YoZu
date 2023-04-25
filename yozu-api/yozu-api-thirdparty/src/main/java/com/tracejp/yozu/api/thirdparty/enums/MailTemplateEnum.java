package com.tracejp.yozu.api.thirdparty.enums;

/**
 * <p> email模板枚举 <p/>
 *
 * @author traceJP
 * @since 2023/4/24 20:14
 */
public enum MailTemplateEnum {

    REGISTER_TEMPLATE((short)1, "Yozu注册邮件", "t_register");

    /**
     * 枚举编号
     */
    private final Short code;

    /**
     * 主题
     */
    private final String subject;

    /**
     * HTML模板
     */
    private final String template;

    MailTemplateEnum(Short code, String subject, String template) {
        this.code = code;
        this.subject = subject;
        this.template = template;
    }

    public Short getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplate() {
        return template;
    }

}
