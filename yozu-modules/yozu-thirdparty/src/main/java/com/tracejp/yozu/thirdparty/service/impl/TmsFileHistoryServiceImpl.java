package com.tracejp.yozu.thirdparty.service.impl;

import com.tracejp.yozu.common.core.utils.DateUtils;
import com.tracejp.yozu.thirdparty.domain.TmsFileHistory;
import com.tracejp.yozu.thirdparty.mapper.TmsFileHistoryMapper;
import com.tracejp.yozu.thirdparty.service.ITmsFileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 上传文件历史Service业务层处理
 *
 * @author tracejp
 * @date 2023-05-03
 */
@Service
public class TmsFileHistoryServiceImpl implements ITmsFileHistoryService {

    @Autowired
    private TmsFileHistoryMapper tmsFileHistoryMapper;

    /**
     * 查询上传文件历史
     *
     * @param id 上传文件历史主键
     * @return 上传文件历史
     */
    @Override
    public TmsFileHistory selectTmsFileHistoryById(Long id) {
        return tmsFileHistoryMapper.selectTmsFileHistoryById(id);
    }

    @Override
    public TmsFileHistory selectFileHistoryByIdentifier(String identifier) {
        return tmsFileHistoryMapper.selectFileHistoryByIdentifier(identifier);
    }

    /**
     * 查询上传文件历史列表
     *
     * @param tmsFileHistory 上传文件历史
     * @return 上传文件历史
     */
    @Override
    public List<TmsFileHistory> selectTmsFileHistoryList(TmsFileHistory tmsFileHistory) {
        return tmsFileHistoryMapper.selectTmsFileHistoryList(tmsFileHistory);
    }

    /**
     * 新增上传文件历史
     *
     * @param tmsFileHistory 上传文件历史
     * @return 结果
     */
    @Override
    public int insertTmsFileHistory(TmsFileHistory tmsFileHistory) {
        tmsFileHistory.setCreateTime(DateUtils.getNowDate());
        return tmsFileHistoryMapper.insertTmsFileHistory(tmsFileHistory);
    }

    /**
     * 修改上传文件历史
     *
     * @param tmsFileHistory 上传文件历史
     * @return 结果
     */
    @Override
    public int updateTmsFileHistory(TmsFileHistory tmsFileHistory) {
        tmsFileHistory.setUpdateTime(DateUtils.getNowDate());
        return tmsFileHistoryMapper.updateTmsFileHistory(tmsFileHistory);
    }

    /**
     * 批量删除上传文件历史
     *
     * @param ids 需要删除的上传文件历史主键
     * @return 结果
     */
    @Override
    public int deleteTmsFileHistoryByIds(Long[] ids) {
        return tmsFileHistoryMapper.deleteTmsFileHistoryByIds(ids);
    }

    /**
     * 删除上传文件历史信息
     *
     * @param id 上传文件历史主键
     * @return 结果
     */
    @Override
    public int deleteTmsFileHistoryById(Long id) {
        return tmsFileHistoryMapper.deleteTmsFileHistoryById(id);
    }

}
