package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.common.core.enums.UserStatus;
import com.tracejp.yozu.common.core.enums.UserType;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.bean.BeanUtils;
import com.tracejp.yozu.common.core.utils.uuid.UUID;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import com.tracejp.yozu.member.mapper.UmsMemberMapper;
import com.tracejp.yozu.member.mapper.UmsMemberRoleMapper;
import com.tracejp.yozu.member.service.IUmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        UmsMember register = new UmsMember();
        register.setPhonenumber(phone);
        BeanUtils.copyBeanProp(register, getBaseMemberInfo());
        int count = umsMemberMapper.insertUmsMember(register);
        if (count <= 0) {
            throw new ServiceException("注册失败，手机号码已存在");
        }

        UmsMember newMember = getMemberByAccount(phone);
        return convertToLoginUser(newMember);
    }

    @Override
    public boolean registerMemberByEmail(UmsMember umsMember) {
        UmsMember register = new UmsMember();
        BeanUtils.copyBeanProp(register, getBaseMemberInfo());

        // 检查邮箱是否唯一
        checkEmailUnique(umsMember.getEmail());

        // 密码加密
        String encodePassword = SecurityUtils.encryptPassword(umsMember.getPassword());
        umsMember.setPassword(encodePassword);

        return umsMemberMapper.insertUmsMember(register) > 0;
    }

    @Override
    public void checkEmailUnique(String email) {
        UmsMember member = getMemberByAccount(email);
        if (member != null) {
            throw new ServiceException("邮箱已存在");
        }
    }

    private UmsMember getBaseMemberInfo() {
        UmsMember member = new UmsMember();
        UmsMemberRole role = umsMemberRoleMapper.selectUmsMemberRoleByDefault();
        member.setRoleId(role.getRoleId());
        member.setUserName(UUID.randomUUID().toString(true));
        member.setStatus(UserStatus.OK.getCode());
        member.setCreateTime(DateUtils.getNowDate());
        return member;
    }

}
