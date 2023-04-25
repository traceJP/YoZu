package com.tracejp.yozu.api.thirdparty.domain;

import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * <p> 邮件消息实体 <p/>
 *
 * @author traceJP
 * @since 2023/4/24 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {

    /**
     * 接收者邮箱号（多个参数以分号;分割）
     */
    @NotEmpty
    private String emails;

    /**
     * 邮件模板
     */
    @NotNull
    private MailTemplateEnum template;

    /**
     * 模板参数
     */
    private Map<String, Object> params;

}
