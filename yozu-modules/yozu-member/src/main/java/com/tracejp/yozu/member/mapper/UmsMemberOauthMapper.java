package com.tracejp.yozu.member.mapper;

import com.tracejp.yozu.member.domain.UmsMemberOauth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社交账号信息Mapper接口
 *
 * @author tracejp
 * @date 2023-04-21
 */
public interface UmsMemberOauthMapper {
    /**
     * 查询社交账号信息
     *
     * @param id 社交账号信息主键
     * @return 社交账号信息
     */
    UmsMemberOauth selectUmsMemberOauthById(Long id);

    /**
     * 查询社交账号信息
     * @param socialUid 社交uid
     * @param type 社交平台类型
     * @return 社交账号信息
     */
    UmsMemberOauth getOauthBySocialCode(@Param("uid") String socialUid, @Param("type") String type);

    /**
     * 查询社交账号信息列表
     *
     * @param umsMemberOauth 社交账号信息
     * @return 社交账号信息集合
     */
    List<UmsMemberOauth> selectUmsMemberOauthList(UmsMemberOauth umsMemberOauth);

    /**
     * 新增社交账号信息
     *
     * @param umsMemberOauth 社交账号信息
     * @return 结果
     */
    int insertUmsMemberOauth(UmsMemberOauth umsMemberOauth);

    /**
     * 修改社交账号信息
     *
     * @param umsMemberOauth 社交账号信息
     * @return 结果
     */
    int updateUmsMemberOauth(UmsMemberOauth umsMemberOauth);

    /**
     * 删除社交账号信息
     *
     * @param id 社交账号信息主键
     * @return 结果
     */
    int deleteUmsMemberOauthById(Long id);

    /**
     * 批量删除社交账号信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteUmsMemberOauthByIds(Long[] ids);
}
