package com.tracejp.yozu.api.thirdparty.domain;

import lombok.Data;

/**
 * <p> 短信响应实体 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 22:50
 */
@Data
public class SmsResult {

    /**
     * 是否成功
     */
    private Boolean isSuccess;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 实际响应体
     * <p>
     * 可自行转换为 SDK 对应的 SendSmsResponse
     */
    private String response;

}
