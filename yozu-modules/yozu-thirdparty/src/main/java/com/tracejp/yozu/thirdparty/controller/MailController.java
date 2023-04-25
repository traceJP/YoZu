package com.tracejp.yozu.thirdparty.controller;

import com.tracejp.yozu.api.thirdparty.domain.param.MailMessageParam;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.thirdparty.component.MailComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * <p> 邮件 Controller <p/>
 *
 * @author traceJP
 * @since 2023/4/24 19:50
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailComponent mailComponent;

    @PostMapping("/send")
    public R<?> send(@RequestBody MailMessageParam param) {
        try {
            mailComponent.send(param);
        } catch (MessagingException e) {
            return R.fail();
        }
        return R.ok();
    }

}
