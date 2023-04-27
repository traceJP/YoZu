package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.api.thirdparty.RemoteThirdpartyService;
import com.tracejp.yozu.api.thirdparty.constant.MailTemplateParamConstants;
import com.tracejp.yozu.api.thirdparty.domain.MailMessage;
import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import com.tracejp.yozu.auth.constant.LoginEmailConstants;
import com.tracejp.yozu.auth.domain.EmailActiveRedisEntity;
import com.tracejp.yozu.auth.form.RegisterBody;
import com.tracejp.yozu.common.core.constant.CacheConstants;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.uuid.UUID;
import com.tracejp.yozu.common.redis.service.RedisService;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> 会员注册服务 <p/>
 *
 * @author traceJP
 * @since 2023/4/23 20:27
 */
@Component
public class UmsRegisterService {

    @Autowired
    private RemoteMemberService remoteMemberService;

    @Autowired
    private RemoteThirdpartyService remoteThirdpartyService;

    @Autowired
    private RedisService redisService;


    public void register(RegisterBody form) {
        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            throw new ServiceException("两次密码不一致");
        }

        String redisKey = CacheConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + form.getEmail();
        EmailActiveRedisEntity active = (EmailActiveRedisEntity) redisService.getCacheObject(redisKey);
        if (active == null) {
            throw new ServiceException("邮箱激活链接已过期");
        }
        if (!active.getActive()) {
            throw new ServiceException("未通过邮箱验证");
        }

        // 删除缓存
        redisService.deleteObject(redisKey);

        // 注册
        UmsMember member = new UmsMember();
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        R<Boolean> register = remoteMemberService.registerMemberInfo(member, SecurityConstants.INNER);
        if (R.FAIL == register.getCode()) {
            throw new ServiceException(register.getMsg());
        }
    }

    public void emailActiveConfirm(String email, String code) {
        String redisKey = CacheConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + email;
        EmailActiveRedisEntity active = (EmailActiveRedisEntity) redisService.getCacheObject(redisKey);
        if (active == null) {
            throw new ServiceException("邮箱激活链接已过期");
        }
        if (!StringUtils.equals(active.getCode(), code)) {
            throw new ServiceException("邮箱激活码错误");
        }
        if (active.getActive()) {
            throw new ServiceException("邮箱已激活");
        }

        // 激活
        active.setActive(true);
        active.setSendTime(System.currentTimeMillis());
        redisService.setCacheObject(
                redisKey,
                active,
                LoginEmailConstants.ACTIVE_CODE_EXPIRE,
                TimeUnit.MILLISECONDS
        );

    }

    public void sendActiveEmail(String email) {
        String redisKey = CacheConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + email;

        // 防刷验证
        long currentTime = System.currentTimeMillis();
        EmailActiveRedisEntity active = (EmailActiveRedisEntity) redisService.getCacheObject(redisKey);
        if (active != null && currentTime - active.getSendTime() < LoginEmailConstants.CODE_REPEAT_EXPIRE) {
            throw new ServiceException("请勿频繁发送邮件");
        }

        // 保存激活码
        String code = UUID.fastUUID().toString(true);
        redisService.setCacheObject(
                redisKey,
                new EmailActiveRedisEntity(code, false, currentTime),
                LoginEmailConstants.ACTIVE_CODE_EXPIRE,
                TimeUnit.MILLISECONDS
        );

        // 邮件发送
        MailMessage mailMessage = new MailMessage();
        mailMessage.setEmails(email);
        mailMessage.setTemplate(MailTemplateEnum.REGISTER_TEMPLATE);
        Map<String, Object> params = new HashMap<>();
        params.put(MailTemplateParamConstants.REGISTER_MAIL, email);
        params.put(MailTemplateParamConstants.REGISTER_CODE, code);
        mailMessage.setParams(params);
        remoteThirdpartyService.sendMail(mailMessage, SecurityConstants.INNER);
    }

}
