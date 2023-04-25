package com.tracejp.yozu.thirdparty.component;

import com.tracejp.yozu.api.thirdparty.domain.param.MailMessageParam;
import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/24 19:59
 */
@Component
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    public void send(MailMessageParam param) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(username);
        helper.setTo(param.getTos().toArray(new String[0]));

        // 模板填充
        MailTemplateEnum template = param.getTemplate();
        helper.setSubject(template.getSubject());
        Context context = new Context(Locale.CHINA, param.getParams());
        String process = templateEngine.process(template.getTemplate(), context);
        helper.setText(process);

        // 发送邮件
        mailSender.send(message);
    }

}
