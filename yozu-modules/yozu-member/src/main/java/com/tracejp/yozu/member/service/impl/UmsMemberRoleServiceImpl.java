package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import com.tracejp.yozu.member.mapper.UmsMemberRoleMapper;
import com.tracejp.yozu.member.service.IUmsMemberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息Service业务层处理
 *
 * @author tracejp
 * @date 2023-04-21
 */
@Service
public class UmsMemberRoleServiceImpl implements IUmsMemberRoleService {
    @Autowired
    private UmsMemberRoleMapper umsMemberRoleMapper;

    /**
     * 查询用户信息
     *
     * @param roleId 用户信息主键
     * @return 用户信息
     */
    @Override
    public UmsMemberRole selectUmsMemberRoleByRoleId(Long roleId) {
        return umsMemberRoleMapper.selectUmsMemberRoleByRoleId(roleId);
    }

    /**
     * 查询用户信息列表
     *
     * @param umsMemberRole 用户信息
     * @return 用户信息
     */
    @Override
    public List<UmsMemberRole> selectUmsMemberRoleList(UmsMemberRole umsMemberRole) {
        return umsMemberRoleMapper.selectUmsMemberRoleList(umsMemberRole);
    }

    /**
     * 新增用户信息
     *
     * @param umsMemberRole 用户信息
     * @return 结果
     */
    @Override
    public int insertUmsMemberRole(UmsMemberRole umsMemberRole) {
        umsMemberRole.setCreateTime(DateUtils.getNowDate());
        return umsMemberRoleMapper.insertUmsMemberRole(umsMemberRole);
    }

    /**
     * 修改用户信息
     *
     * @param umsMemberRole 用户信息
     * @return 结果
     */
    @Override
    public int updateUmsMemberRole(UmsMemberRole umsMemberRole) {
        umsMemberRole.setUpdateTime(DateUtils.getNowDate());
        return umsMemberRoleMapper.updateUmsMemberRole(umsMemberRole);
    }

    /**
     * 批量删除用户信息
     *
     * @param roleIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberRoleByRoleIds(Long[] roleIds) {
        return umsMemberRoleMapper.deleteUmsMemberRoleByRoleIds(roleIds);
    }

    /**
     * 删除用户信息信息
     *
     * @param roleId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberRoleByRoleId(Long roleId) {
        return umsMemberRoleMapper.deleteUmsMemberRoleByRoleId(roleId);
    }
}
