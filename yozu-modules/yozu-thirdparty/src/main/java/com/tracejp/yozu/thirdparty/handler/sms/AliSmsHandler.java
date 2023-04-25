package com.tracejp.yozu.thirdparty.handler.sms;

import com.alibaba.fastjson2.JSON;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;

/**
 * <p> AliSmsHandler实现类 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 15:18
 */
public class AliSmsHandler implements ISmsHandler {

    private AsyncClient smsAsyncClient;

    private String signName;

    public AliSmsHandler(AsyncClient smsAsyncClient, String signName) {
        this.smsAsyncClient = smsAsyncClient;
        this.signName = signName;
    }

    @Override
    public void send(SmsMessage message) {
        // 构造请求
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(message.getPhones())
                .signName(signName)
                .templateCode(message.getTemplateId())
                .templateParam(JSON.toJSONString(message.getParam()))
                .build();

        // 发送短信
        smsAsyncClient.sendSms(sendSmsRequest);
    }

}
