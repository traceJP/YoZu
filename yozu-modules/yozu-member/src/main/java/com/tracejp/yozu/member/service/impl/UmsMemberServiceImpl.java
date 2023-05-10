package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.api.thirdparty.RemoteThirdpartyService;
import com.tracejp.yozu.api.thirdparty.constant.MailTemplateParamConstants;
import com.tracejp.yozu.api.thirdparty.constant.SmsTemplateParamConstants;
import com.tracejp.yozu.api.thirdparty.domain.MailMessage;
import com.tracejp.yozu.api.thirdparty.domain.SmsMessage;
import com.tracejp.yozu.api.thirdparty.enums.MailTemplateEnum;
import com.tracejp.yozu.api.thirdparty.enums.SmsTemplateEnum;
import com.tracejp.yozu.common.core.constant.CaptchaConstants;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.redis.EmailActiveRedisEntity;
import com.tracejp.yozu.common.core.domain.redis.SmsCaptchaRedisEntity;
import com.tracejp.yozu.common.core.enums.UserStatus;
import com.tracejp.yozu.common.core.enums.UserType;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.bean.BeanUtils;
import com.tracejp.yozu.common.core.utils.uuid.UUID;
import com.tracejp.yozu.common.redis.service.RedisService;
import com.tracejp.yozu.common.security.service.TokenService;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.domain.dto.UmsMemberDTO;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import com.tracejp.yozu.member.domain.UmsMemberOauth;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import com.tracejp.yozu.member.mapper.UmsMemberMapper;
import com.tracejp.yozu.member.mapper.UmsMemberOauthMapper;
import com.tracejp.yozu.member.mapper.UmsMemberRoleMapper;
import com.tracejp.yozu.member.service.IUmsMemberService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息Service业务层处理
 *
 * @author tracejp
 * @date 2023-04-21
 */
@Service
public class UmsMemberServiceImpl implements IUmsMemberService {
    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Autowired
    private UmsMemberRoleMapper umsMemberRoleMapper;

    @Autowired
    private UmsMemberOauthMapper umsMemberOauthMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RemoteThirdpartyService remoteThirdpartyService;


    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public UmsMember selectUmsMemberByUserId(Long userId) {
        return umsMemberMapper.selectUmsMemberByUserId(userId);
    }

    /**
     * 查询用户信息列表
     *
     * @param umsMember 用户信息
     * @return 用户信息
     */
    @Override
    public List<UmsMember> selectUmsMemberList(UmsMember umsMember) {
        return umsMemberMapper.selectUmsMemberList(umsMember);
    }

    /**
     * 新增用户信息
     *
     * @param umsMember 用户信息
     * @return 结果
     */
    @Override
    public int insertUmsMember(UmsMember umsMember) {
        umsMember.setCreateTime(DateUtils.getNowDate());
        return umsMemberMapper.insertUmsMember(umsMember);
    }

    /**
     * 修改用户信息
     *
     * @param umsMember 用户信息
     * @return 结果
     */
    @Override
    public int updateUmsMember(UmsMember umsMember) {
        umsMember.setUpdateTime(DateUtils.getNowDate());
        return umsMemberMapper.updateUmsMember(umsMember);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberByUserIds(Long[] userIds) {
        return umsMemberMapper.deleteUmsMemberByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberByUserId(Long userId) {
        return umsMemberMapper.deleteUmsMemberByUserId(userId);
    }

    @Override
    public UmsMember getMemberByAccount(String account) {
        return umsMemberMapper.getMemberByAccount(account);
    }

    @Override
    public UmsMember getMemberBySocialCode(String socialCode, SocialTypeEnum type) {
        UmsMemberOauth social = umsMemberOauthMapper.getOauthBySocialCode(socialCode, type.getCode());
        if (social == null) {
            return null;
        }

        // 查询用户信息
        return umsMemberMapper.selectUmsMemberByUserId(social.getUserId());
    }

    @Override
    public LoginUser convertToLoginUser(UmsMember member) {
        LoginUser memberVo = new LoginUser();
        memberVo.setUserInfo(member);
        memberVo.setUserid(member.getUserId());
        memberVo.setUsername(member.getUserName());
        memberVo.setUserType(UserType.MEMBER_USER);

        // 查询权限
        UmsMemberRole role = umsMemberRoleMapper.selectUmsMemberRoleByRoleId(member.getRoleId());
        Set<String> roles = new HashSet<>(1);
        roles.add(role.getRoleKey());
        memberVo.setRoles(roles);

        return memberVo;
    }

    @Override
    public LoginUser getMemberOrRegister(String phone) {
        UmsMember member = getMemberByAccount(phone);
        if (StringUtils.isNotNull(member)) {
            return convertToLoginUser(member);
        }

        UmsMember insert = setBaseMemberInfo(new UmsMember());
        insert.setPhonenumber(phone);
        int count = umsMemberMapper.insertUmsMember(insert);
        if (count <= 0) {
            throw new ServiceException("注册失败，手机号码已存在");
        }

        UmsMember newMember = getMemberByAccount(phone);
        return convertToLoginUser(newMember);
    }

    @Override
    public LoginUser registerMemberByEmail(UmsMember umsMember) {
        // 检查邮箱是否唯一
        checkEmailUnique(umsMember.getEmail());

        // 密码加密
        String encodePassword = SecurityUtils.encryptPassword(umsMember.getPassword());
        umsMember.setPassword(encodePassword);

        // 插入记录
        UmsMember member = setBaseMemberInfo(umsMember);
        int inserted = umsMemberMapper.insertUmsMember(member);
        if (inserted <= 0) {
            throw new ServiceException("注册失败");
        }

        return convertToLoginUser(member);
    }

    @Transactional
    @Override
    public LoginUser registerMemberBySocial(UmsMemberDTO umsMemberDTO) {
        // 注册账号
        UmsMember member = umsMemberDTO.convertTo();
        UmsMember insert = setBaseMemberInfo(member);
        this.insertUmsMember(insert);

        // 绑定社交 uid
        UmsMemberOauth umsMemberOauth = new UmsMemberOauth();
        umsMemberOauth.setUserId(insert.getUserId());
        umsMemberOauth.setSocialId(umsMemberDTO.getSocialUid());
        umsMemberOauth.setSocialType(umsMemberDTO.getSocialType().getCode());
        umsMemberOauth.setSocialData(umsMemberDTO.getSocialData());
        umsMemberOauth.setCreateTime(DateUtils.getNowDate());
        umsMemberOauthMapper.insertUmsMemberOauth(umsMemberOauth);

        return convertToLoginUser(insert);
    }

    @Override
    public void checkEmailUnique(String email) {
        UmsMember member = getMemberByAccount(email);
        if (member != null) {
            throw new ServiceException("邮箱已存在");
        }
    }

    @Override
    public void updateCurrentMemberLoginCache(UmsMember update) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UmsMember userInfo = loginUser.getUserInfo(UmsMember.class);
        BeanUtils.updateProperties(update, userInfo);
        loginUser.setUserInfo(userInfo);
        tokenService.setLoginUser(loginUser);
    }

    private UmsMember setBaseMemberInfo(UmsMember member) {
        UmsMemberRole role = umsMemberRoleMapper.selectUmsMemberRoleByDefault();
        member.setRoleId(role.getRoleId());
        member.setUserName(UUID.randomUUID().toString(true));
        member.setStatus(UserStatus.OK.getCode());
        member.setCreateTime(DateUtils.getNowDate());
        return member;
    }

    @Override
    public void updateEmail(UmsMember userInfo) {
        String redisKey = CaptchaConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + userInfo.getEmail();
        EmailActiveRedisEntity active = (EmailActiveRedisEntity) redisService.getCacheObject(redisKey);
        if (active == null) {
            throw new ServiceException("邮箱激活链接已过期");
        }
        if (!active.getActive()) {
            throw new ServiceException("未通过邮箱验证");
        }
        updateUmsMember(userInfo);
    }

    @Override
    public void updatePhone(UmsMember userInfo, String code) {
        // 验证码判断
        String redisKey = CaptchaConstants.AUTH_SMS_CAPTCHA_KEY + userInfo.getPhonenumber();
        SmsCaptchaRedisEntity captcha = (SmsCaptchaRedisEntity) redisService.getCacheObject(redisKey);
        if (captcha == null) {
            throw new ServiceException("验证码失效，请重新发送");
        }
        if (!StringUtils.equals(captcha.getCode(), code)) {
            throw new ServiceException("验证码错误，请重新输入");
        }

        updateUmsMember(userInfo);
    }

    @Override
    public void sendEmailCaptcha(String email) {
        String redisKey = CaptchaConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + email;

        // 防刷验证
        long currentTime = System.currentTimeMillis();
        EmailActiveRedisEntity active = (EmailActiveRedisEntity) redisService.getCacheObject(redisKey);
        if (active != null && currentTime - active.getSendTime() < CaptchaConstants.CODE_REPEAT_EXPIRE) {
            throw new ServiceException("请勿频繁发送邮件");
        }

        // 保存激活码
        String code = UUID.fastUUID().toString(true);
        redisService.setCacheObject(
                redisKey,
                new EmailActiveRedisEntity(code, false, currentTime),
                CaptchaConstants.ACTIVE_CODE_EXPIRE,
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

    @Override
    public void activeVerifyEmail(String email, String code) {
        String redisKey = CaptchaConstants.AUTH_EMAIL_ACTIVE_CODE_KEY + email;
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
                CaptchaConstants.ACTIVE_CODE_EXPIRE,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void sendSmsCaptcha(String phone) {
        String redisKey = CaptchaConstants.AUTH_SMS_CAPTCHA_KEY + phone;

        // 防刷
        long currentTime = System.currentTimeMillis();
        SmsCaptchaRedisEntity smsCaptcha = (SmsCaptchaRedisEntity) redisService.getCacheObject(redisKey);
        if (smsCaptcha != null && currentTime - smsCaptcha.getSendTime() < CaptchaConstants.CAPTCHA_REPEAT_EXPIRE) {
            throw new ServiceException("请勿频繁发送验证码");
        }

        // 保存验证码
        String code = RandomStringUtils.randomNumeric(CaptchaConstants.CAPTCHA_COUNT);
        redisService.setCacheObject(
                redisKey,
                new SmsCaptchaRedisEntity(code, currentTime),
                CaptchaConstants.CAPTCHA_EXPIRE,
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
