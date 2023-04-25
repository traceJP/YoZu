package com.tracejp.yozu.thirdparty.handler.mail;

import com.tracejp.yozu.api.thirdparty.domain.MailMessage;

import javax.mail.MessagingException;

/**
 * <p> MailHandler接口 <p/>
 *
 * @author traceJP
 * @since 2023/4/24 19:59
 */
public interface IMailHandler {

    /**
     * 发送邮件
     * @param message 邮件信息实体
     */
    void send(MailMessage message) throws MessagingException;

}
