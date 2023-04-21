package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.mapper.UmsMemberMapper;
import com.tracejp.yozu.member.service.IUmsMemberService;
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
public class UmsMemberServiceImpl implements IUmsMemberService {
    @Autowired
    private UmsMemberMapper umsMemberMapper;

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
}
