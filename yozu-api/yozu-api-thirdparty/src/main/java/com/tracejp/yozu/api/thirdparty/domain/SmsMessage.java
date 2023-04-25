package com.tracejp.yozu.api.thirdparty.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/25 22:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsMessage {

    /**
     * 接收者手机号（多个用分号;分割）
     */
    @NotEmpty
    private String phones;

    /**
     * 短信模板Id
     */
    @NotEmpty
    private String templateId;

    /**
     * 短信模板参数
     */
    private Map<String, String> param;

}
