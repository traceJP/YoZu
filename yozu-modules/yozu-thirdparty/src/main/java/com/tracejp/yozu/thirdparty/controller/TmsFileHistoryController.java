package com.tracejp.yozu.thirdparty.controller;

import com.tracejp.yozu.common.core.utils.poi.ExcelUtil;
import com.tracejp.yozu.common.core.web.controller.BaseController;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;
import com.tracejp.yozu.common.core.web.page.TableDataInfo;
import com.tracejp.yozu.common.log.annotation.Log;
import com.tracejp.yozu.common.log.enums.BusinessType;
import com.tracejp.yozu.common.security.annotation.RequiresPermissions;
import com.tracejp.yozu.thirdparty.domain.TmsFileHistory;
import com.tracejp.yozu.thirdparty.service.ITmsFileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 上传文件历史Controller
 * 
 * @author tracejp
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/history")
public class TmsFileHistoryController extends BaseController {

    @Autowired
    private ITmsFileHistoryService tmsFileHistoryService;


    /**
     * 查询上传文件历史列表
     */
    @RequiresPermissions("thirdparty:history:list")
    @GetMapping("/list")
    public TableDataInfo list(TmsFileHistory tmsFileHistory) {
        startPage();
        List<TmsFileHistory> list = tmsFileHistoryService.selectTmsFileHistoryList(tmsFileHistory);
        return getDataTable(list);
    }

    /**
     * 导出上传文件历史列表
     */
    @RequiresPermissions("thirdparty:history:export")
    @Log(title = "上传文件历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TmsFileHistory tmsFileHistory) {
        List<TmsFileHistory> list = tmsFileHistoryService.selectTmsFileHistoryList(tmsFileHistory);
        ExcelUtil<TmsFileHistory> util = new ExcelUtil<TmsFileHistory>(TmsFileHistory.class);
        util.exportExcel(response, list, "上传文件历史数据");
    }

    /**
     * 获取上传文件历史详细信息
     */
    @RequiresPermissions("thirdparty:history:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tmsFileHistoryService.selectTmsFileHistoryById(id));
    }

    /**
     * 新增上传文件历史
     */
    @RequiresPermissions("thirdparty:history:add")
    @Log(title = "上传文件历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TmsFileHistory tmsFileHistory) {
        return toAjax(tmsFileHistoryService.insertTmsFileHistory(tmsFileHistory));
    }

    /**
     * 修改上传文件历史
     */
    @RequiresPermissions("thirdparty:history:edit")
    @Log(title = "上传文件历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TmsFileHistory tmsFileHistory) {
        return toAjax(tmsFileHistoryService.updateTmsFileHistory(tmsFileHistory));
    }

    /**
     * 删除上传文件历史
     */
    @RequiresPermissions("thirdparty:history:remove")
    @Log(title = "上传文件历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tmsFileHistoryService.deleteTmsFileHistoryByIds(ids));
    }

}
