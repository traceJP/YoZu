package com.tracejp.yozu.thirdparty.controller;

import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.security.annotation.InnerAuth;
import com.tracejp.yozu.thirdparty.handler.sms.ISmsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 短信 Controller <p/>
 *
 * @author traceJP
 * @since 2023/4/24 20:01
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private ISmsHandler smsHandler;

    @InnerAuth
    @PostMapping("/send")
    public R<?> send(@RequestBody SmsMessage message) {
        smsHandler.send(message);
        return R.ok();
    }

}
