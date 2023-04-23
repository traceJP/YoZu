package com.tracejp.yozu.member.mapper;

import com.tracejp.yozu.member.domain.UmsMemberRole;

import java.util.List;

/**
 * 用户角色信息Mapper接口
 *
 * @author tracejp
 * @date 2023-04-21
 */
public interface UmsMemberRoleMapper {


    /**
     * 查询默认用户角色
     * @return
     */
    UmsMemberRole selectUmsMemberRoleByDefault();
    
    /**
     * 查询用户角色信息
     *
     * @param roleId 用户角色信息主键
     * @return 用户角色信息
     */
    UmsMemberRole selectUmsMemberRoleByRoleId(Long roleId);

    /**
     * 查询用户角色信息列表
     *
     * @param umsMemberRole 用户角色信息
     * @return 用户角色信息集合
     */
    List<UmsMemberRole> selectUmsMemberRoleList(UmsMemberRole umsMemberRole);

    /**
     * 新增用户角色信息
     *
     * @param umsMemberRole 用户角色信息
     * @return 结果
     */
    int insertUmsMemberRole(UmsMemberRole umsMemberRole);

    /**
     * 修改用户角色信息
     *
     * @param umsMemberRole 用户角色信息
     * @return 结果
     */
    int updateUmsMemberRole(UmsMemberRole umsMemberRole);

    /**
     * 删除用户角色信息
     *
     * @param roleId 用户角色信息主键
     * @return 结果
     */
    int deleteUmsMemberRoleByRoleId(Long roleId);

    /**
     * 批量删除用户角色信息
     *
     * @param roleIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteUmsMemberRoleByRoleIds(Long[] roleIds);
}
