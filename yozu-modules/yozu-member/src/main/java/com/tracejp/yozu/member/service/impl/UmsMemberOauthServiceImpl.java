package com.tracejp.yozu.member.service.impl;

import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.member.domain.UmsMemberOauth;
import com.tracejp.yozu.member.mapper.UmsMemberOauthMapper;
import com.tracejp.yozu.member.service.IUmsMemberOauthService;
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
public class UmsMemberOauthServiceImpl implements IUmsMemberOauthService {
    @Autowired
    private UmsMemberOauthMapper umsMemberOauthMapper;

    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    @Override
    public UmsMemberOauth selectUmsMemberOauthById(Long id) {
        return umsMemberOauthMapper.selectUmsMemberOauthById(id);
    }

    /**
     * 查询用户信息列表
     *
     * @param umsMemberOauth 用户信息
     * @return 用户信息
     */
    @Override
    public List<UmsMemberOauth> selectUmsMemberOauthList(UmsMemberOauth umsMemberOauth) {
        return umsMemberOauthMapper.selectUmsMemberOauthList(umsMemberOauth);
    }

    /**
     * 新增用户信息
     *
     * @param umsMemberOauth 用户信息
     * @return 结果
     */
    @Override
    public int insertUmsMemberOauth(UmsMemberOauth umsMemberOauth) {
        umsMemberOauth.setCreateTime(DateUtils.getNowDate());
        return umsMemberOauthMapper.insertUmsMemberOauth(umsMemberOauth);
    }

    /**
     * 修改用户信息
     *
     * @param umsMemberOauth 用户信息
     * @return 结果
     */
    @Override
    public int updateUmsMemberOauth(UmsMemberOauth umsMemberOauth) {
        umsMemberOauth.setUpdateTime(DateUtils.getNowDate());
        return umsMemberOauthMapper.updateUmsMemberOauth(umsMemberOauth);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberOauthByIds(Long[] ids) {
        return umsMemberOauthMapper.deleteUmsMemberOauthByIds(ids);
    }

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUmsMemberOauthById(Long id) {
        return umsMemberOauthMapper.deleteUmsMemberOauthById(id);
    }
}
