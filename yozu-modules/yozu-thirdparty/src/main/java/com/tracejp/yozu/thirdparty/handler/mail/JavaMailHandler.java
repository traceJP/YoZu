package com.tracejp.yozu.thirdparty.handler.mail;

import com.tracejp.yozu.api.thirdparty.domain.MailMessage;
import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import com.tracejp.yozu.common.core.exception.ServiceException;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> JavaMailHandler实现类 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 15:56
 */
@Component
public class JavaMailHandler implements IMailHandler {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void send(MailMessage message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(username);
        helper.setTo(message.getEmails().split(";"));
        CompletableFuture.runAsync(() -> {
            // 模板填充
            MailTemplateEnum template = message.getTemplate();
            try {
                helper.setSubject(template.getSubject());
                Context context = new Context(Locale.CHINA, message.getParams());
                String process = templateEngine.process(template.getTemplate(), context);
                helper.setText(process);
            } catch (MessagingException e) {
                throw new ServiceException("邮件模板解析失败：" + e);
            }
            // 发送邮件
            mailSender.send(mimeMessage);
        }, threadPoolExecutor);
    }

}
