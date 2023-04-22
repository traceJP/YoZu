package com.tracejp.yozu.member.mapper;

import com.tracejp.yozu.member.api.domain.UmsMember;

import java.util.List;

/**
 * 用户信息Mapper接口
 *
 * @author tracejp
 * @date 2023-04-21
 */
public interface UmsMemberMapper {
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
     * 删除用户信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    int deleteUmsMemberByUserId(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteUmsMemberByUserIds(Long[] userIds);

    UmsMember getMemberByAccount(String account);

}
