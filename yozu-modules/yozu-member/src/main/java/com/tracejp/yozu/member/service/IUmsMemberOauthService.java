package com.tracejp.yozu.member.service;

import com.tracejp.yozu.member.domain.UmsMemberOauth;

import java.util.List;

/**
 * 用户信息Service接口
 *
 * @author tracejp
 * @date 2023-04-21
 */
public interface IUmsMemberOauthService {
    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    UmsMemberOauth selectUmsMemberOauthById(Long id);

    /**
     * 查询用户信息列表
     *
     * @param umsMemberOauth 用户信息
     * @return 用户信息集合
     */
    List<UmsMemberOauth> selectUmsMemberOauthList(UmsMemberOauth umsMemberOauth);

    /**
     * 新增用户信息
     *
     * @param umsMemberOauth 用户信息
     * @return 结果
     */
    int insertUmsMemberOauth(UmsMemberOauth umsMemberOauth);

    /**
     * 修改用户信息
     *
     * @param umsMemberOauth 用户信息
     * @return 结果
     */
    int updateUmsMemberOauth(UmsMemberOauth umsMemberOauth);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键集合
     * @return 结果
     */
    int deleteUmsMemberOauthByIds(Long[] ids);

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    int deleteUmsMemberOauthById(Long id);
}
