package com.tracejp.yozu.api.thirdparty;

import com.tracejp.yozu.api.thirdparty.domain.MailMessage;
import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.constant.ServiceNameConstants;
import com.tracejp.yozu.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * <p> 第三方服务接口 <p/>
 *
 * @author traceJP
 * @since 2023/4/24 19:54
 */
@FeignClient(contextId = "remoteThirdpartyService", value = ServiceNameConstants.THIRDPARTY_SERVICE)
public interface RemoteThirdpartyService {

    /**
     * 发送邮件
     * @param message 邮件信息
     */
    @PostMapping("/mail/send")
    R<?> sendMail(@RequestBody MailMessage message, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 发送短信
     * @param message 短信信息
     */
    @PostMapping("/sms/send")
    R<?> sendSms(@RequestBody SmsMessage message, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
