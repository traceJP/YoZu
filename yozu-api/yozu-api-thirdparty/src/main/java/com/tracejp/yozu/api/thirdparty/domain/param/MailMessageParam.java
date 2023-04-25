package com.tracejp.yozu.api.thirdparty.domain.param;

import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/24 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageParam {

    /**
     * 接收者
     */
    @NotEmpty
    private List<String> tos;

    /**
     * 邮件模板
     */
    @NotNull
    private MailTemplateEnum template;

    /**
     * 模板参数
     */
    private Map<String, Object> params;

    public MailMessageParam(String to, MailTemplateEnum template, Map<String, Object> params) {
        this.tos = Collections.singletonList(to);
        this.template = template;
        this.params = params;
    }

}
