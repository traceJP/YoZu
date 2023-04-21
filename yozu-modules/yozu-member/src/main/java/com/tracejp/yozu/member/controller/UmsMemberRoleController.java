package com.tracejp.yozu.member.controller;

import com.tracejp.yozu.common.core.utils.poi.ExcelUtil;
import com.tracejp.yozu.common.core.web.controller.BaseController;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;
import com.tracejp.yozu.common.core.web.page.TableDataInfo;
import com.tracejp.yozu.common.log.annotation.Log;
import com.tracejp.yozu.common.log.enums.BusinessType;
import com.tracejp.yozu.common.security.annotation.RequiresPermissions;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import com.tracejp.yozu.member.service.IUmsMemberRoleService;
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
@RequestMapping("/role")
public class UmsMemberRoleController extends BaseController {
    @Autowired
    private IUmsMemberRoleService umsMemberRoleService;

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("member:role:list")
    @GetMapping("/list")
    public TableDataInfo list(UmsMemberRole umsMemberRole) {
        startPage();
        List<UmsMemberRole> list = umsMemberRoleService.selectUmsMemberRoleList(umsMemberRole);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("member:role:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UmsMemberRole umsMemberRole) {
        List<UmsMemberRole> list = umsMemberRoleService.selectUmsMemberRoleList(umsMemberRole);
        ExcelUtil<UmsMemberRole> util = new ExcelUtil<UmsMemberRole>(UmsMemberRole.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @RequiresPermissions("member:role:query")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable("roleId") Long roleId) {
        return success(umsMemberRoleService.selectUmsMemberRoleByRoleId(roleId));
    }

    /**
     * 新增用户信息
     */
    @RequiresPermissions("member:role:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsMemberRole umsMemberRole) {
        return toAjax(umsMemberRoleService.insertUmsMemberRole(umsMemberRole));
    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("member:role:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMemberRole umsMemberRole) {
        return toAjax(umsMemberRoleService.updateUmsMemberRole(umsMemberRole));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("member:role:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds) {
        return toAjax(umsMemberRoleService.deleteUmsMemberRoleByRoleIds(roleIds));
    }
}
