package com.tracejp.yozu.member.controller;

import com.tracejp.yozu.common.core.utils.poi.ExcelUtil;
import com.tracejp.yozu.common.core.web.controller.BaseController;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;
import com.tracejp.yozu.common.core.web.page.TableDataInfo;
import com.tracejp.yozu.common.log.annotation.Log;
import com.tracejp.yozu.common.log.enums.BusinessType;
import com.tracejp.yozu.common.security.annotation.RequiresPermissions;
import com.tracejp.yozu.member.domain.UmsMemberOauth;
import com.tracejp.yozu.member.service.IUmsMemberOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息Controller
 *
 * @author tracejp
 * @date 2023-04-21
 */
@RestController
@RequestMapping("/oauth")
public class UmsMemberOauthController extends BaseController {
    @Autowired
    private IUmsMemberOauthService umsMemberOauthService;

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("member:oauth:list")
    @GetMapping("/list")
    public TableDataInfo list(UmsMemberOauth umsMemberOauth) {
        startPage();
        List<UmsMemberOauth> list = umsMemberOauthService.selectUmsMemberOauthList(umsMemberOauth);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("member:oauth:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UmsMemberOauth umsMemberOauth) {
        List<UmsMemberOauth> list = umsMemberOauthService.selectUmsMemberOauthList(umsMemberOauth);
        ExcelUtil<UmsMemberOauth> util = new ExcelUtil<UmsMemberOauth>(UmsMemberOauth.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @RequiresPermissions("member:oauth:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(umsMemberOauthService.selectUmsMemberOauthById(id));
    }

    /**
     * 新增用户信息
     */
    @RequiresPermissions("member:oauth:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsMemberOauth umsMemberOauth) {
        return toAjax(umsMemberOauthService.insertUmsMemberOauth(umsMemberOauth));
    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("member:oauth:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMemberOauth umsMemberOauth) {
        return toAjax(umsMemberOauthService.updateUmsMemberOauth(umsMemberOauth));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("member:oauth:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsMemberOauthService.deleteUmsMemberOauthByIds(ids));
    }
}
