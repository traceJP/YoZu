package com.tracejp.yozu.thirdparty.mapper;

import com.tracejp.yozu.thirdparty.domain.TmsFileHistory;

import java.util.List;

/**
 * 上传文件历史Mapper接口
 *
 * @author tracejp
 * @date 2023-05-03
 */
public interface TmsFileHistoryMapper {
    /**
     * 查询上传文件历史
     *
     * @param id 上传文件历史主键
     * @return 上传文件历史
     */
    TmsFileHistory selectTmsFileHistoryById(Long id);

    /**
     * 通过identifier查询文件实体
     * @param identifier 文件标识
     * @return 文件实体
     */
    TmsFileHistory selectFileHistoryByIdentifier(String identifier);

    /**
     * 查询上传文件历史列表
     *
     * @param tmsFileHistory 上传文件历史
     * @return 上传文件历史集合
     */
    List<TmsFileHistory> selectTmsFileHistoryList(TmsFileHistory tmsFileHistory);

    /**
     * 新增上传文件历史
     *
     * @param tmsFileHistory 上传文件历史
     * @return 结果
     */
    int insertTmsFileHistory(TmsFileHistory tmsFileHistory);

    /**
     * 修改上传文件历史
     *
     * @param tmsFileHistory 上传文件历史
     * @return 结果
     */
    int updateTmsFileHistory(TmsFileHistory tmsFileHistory);

    /**
     * 删除上传文件历史
     *
     * @param id 上传文件历史主键
     * @return 结果
     */
    int deleteTmsFileHistoryById(Long id);

    /**
     * 批量删除上传文件历史
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTmsFileHistoryByIds(Long[] ids);

}
