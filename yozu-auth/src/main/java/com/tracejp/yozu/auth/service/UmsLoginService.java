package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.api.thirdparty.RemoteThirdpartyService;
import com.tracejp.yozu.api.thirdparty.constant.SmsTemplateParamConstants;
import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;
import com.tracejp.yozu.api.thirdparty.enums.SmsTemplateEnum;
import com.tracejp.yozu.auth.constant.LoginSmsConstants;
import com.tracejp.yozu.auth.domain.SmsCaptchaRedisEntity;
import com.tracejp.yozu.auth.form.LoginAccountBody;
import com.tracejp.yozu.auth.form.LoginSmsBody;
import com.tracejp.yozu.common.core.constant.CacheConstants;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.redis.service.RedisService;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> 注册用户登录校验方法 <p/>
 *
 * @author traceJP
 * @since 2023/4/23 15:48
 */
@Component
public class UmsLoginService extends AbsLoginService {

    @Autowired
    private RemoteMemberService remoteMemberService;

    @Autowired
    private RemoteThirdpartyService remoteThirdpartyService;

    @Autowired
    private RedisService redisService;


    public LoginUser loginByAccount(LoginAccountBody form) {

        R<LoginUser> memberResult = remoteMemberService.getMemberInfo(form.getAccount(), SecurityConstants.INNER);
        if (StringUtils.isNull(memberResult) || StringUtils.isNull(memberResult.getData())) {
            throw new ServiceException("用户名或密码错误");
        }
        if (R.FAIL == memberResult.getCode()) {
            throw new ServiceException(memberResult.getMsg());
        }
        LoginUser userInfo = memberResult.getData();
        UmsMember memberInfo = userInfo.getUserInfo(UmsMember.class);

        // 密码比对
        matchesPassword(form.getPassword(), memberInfo.getPassword());

        return userInfo;
    }

    public LoginUser loginBySms(LoginSmsBody form) {
        // 验证码判断
        String redisKey = CacheConstants.AUTH_SMS_CAPTCHA_KEY + form.getPhone();
        SmsCaptchaRedisEntity captcha = (SmsCaptchaRedisEntity) redisService.getCacheObject(redisKey);
        if (captcha == null) {
            throw new ServiceException("验证码失效，请重新发送");
        }
        if (!StringUtils.equals(captcha.getCode(), form.getCode())) {
            throw new ServiceException("验证码错误，请重新输入");
        }

        // 缓存删除
        redisService.deleteObject(redisKey);

        // 登录或注册
        R<LoginUser> memberResult = remoteMemberService.getMemberInfoOrRegister(form.getPhone(), SecurityConstants.INNER);
        if (StringUtils.isNull(memberResult)
                || StringUtils.isNull(memberResult.getData())
                || R.FAIL == memberResult.getCode()) {
            throw new ServiceException(memberResult.getMsg());
        }
        return memberResult.getData();
    }

    public void sendSmsCaptcha(String phone) {
        String redisKey = CacheConstants.AUTH_SMS_CAPTCHA_KEY + phone;

        // 防刷
        long currentTime = System.currentTimeMillis();
        SmsCaptchaRedisEntity smsCaptcha = (SmsCaptchaRedisEntity) redisService.getCacheObject(redisKey);
        if (smsCaptcha != null && currentTime - smsCaptcha.getSendTime() < LoginSmsConstants.CAPTCHA_REPEAT_EXPIRE) {
            throw new ServiceException("请勿频繁发送验证码");
        }

        // 保存验证码
        String code = RandomStringUtils.randomNumeric(LoginSmsConstants.CAPTCHA_COUNT);
        redisService.setCacheObject(
                redisKey,
                new SmsCaptchaRedisEntity(code, currentTime),
                LoginSmsConstants.CAPTCHA_EXPIRE,
                TimeUnit.MILLISECONDS
        );

        // 发送验证码
        SmsMessage message = new SmsMessage();
        message.setPhones(phone);
        message.setTemplateId(SmsTemplateEnum.LOGIN_CAPTCHA_TEMPLATE.getTemplateId());
        Map<String, String> param = new HashMap<>();
        param.put(SmsTemplateParamConstants.LOGIN_CAPTCHA_CODE, code);
        message.setParam(param);
        remoteThirdpartyService.sendSms(message, SecurityConstants.INNER);
    }

}
