package com.tracejp.yozu.member.service;

import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.domain.UmsMember;

import java.util.List;

/**
 * 用户信息Service接口
 *
 * @author tracejp
 * @date 2023-04-21
 */
public interface IUmsMemberService {
    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    UmsMember selectUmsMemberByUserId(Long userId);

    /**
     * 查询用户信息列表
     *
     * @param umsMember 用户信息
     * @return 用户信息集合
     */
    List<UmsMember> selectUmsMemberList(UmsMember umsMember);

    /**
     * 新增用户信息
     *
     * @param umsMember 用户信息
     * @return 结果
     */
    int insertUmsMember(UmsMember umsMember);

    /**
     * 修改用户信息
     *
     * @param umsMember 用户信息
     * @return 结果
     */
    int updateUmsMember(UmsMember umsMember);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息主键集合
     * @return 结果
     */
    int deleteUmsMemberByUserIds(Long[] userIds);

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    int deleteUmsMemberByUserId(Long userId);

    /**
     * 获取用户信息通过账号（邮箱或手机）
     * @param account 邮箱 手机
     * @return 结果
     */
    UmsMember getMemberByAccount(String account);

    /**
     * 将用户实体转换为登录用户实体
     * @param member UmsMember
     * @return LoginUser
     */
    LoginUser convertToLoginUser(UmsMember member);

    /**
     * 获取用户信息通过手机号，如果没有手机号则注册一个新账号
     * @param phone 手机号
     * @return 结果
     */
    LoginUser getMemberOrRegister(String phone);

    /**
     * 通过邮箱注册用户
     * @param umsMember 邮箱 密码
     * @return
     */
    boolean registerMemberByEmail(UmsMember umsMember);

    /**
     * 检查邮箱是否唯一，不唯一则抛出异常
     * @param email 邮箱
     */
    void checkEmailUnique(String email);

}
