package com.tracejp.yozu.thirdparty.handler.sms;

import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;

/**
 * <p> SmsHandler接口 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 15:17
 */
public interface ISmsHandler {

    /**
     * 发送短信
     *
     * @param message 短信信息实体
     */
    void send(SmsMessage message);

}
