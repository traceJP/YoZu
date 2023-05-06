package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.common.core.enums.UserStatus;
import com.tracejp.yozu.common.core.enums.UserType;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.bean.BeanUtils;
import com.tracejp.yozu.common.core.utils.uuid.UUID;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
