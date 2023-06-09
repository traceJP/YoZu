package com.tracejp.yozu.member.controller;

import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.poi.ExcelUtil;
import com.tracejp.yozu.common.core.web.controller.BaseController;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;
import com.tracejp.yozu.common.core.web.page.TableDataInfo;
import com.tracejp.yozu.common.log.annotation.Log;
import com.tracejp.yozu.common.log.enums.BusinessType;
import com.tracejp.yozu.common.security.annotation.RequiresPermissions;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.domain.dto.UmsMemberDTO;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import com.tracejp.yozu.member.service.IUmsMemberRoleService;
import com.tracejp.yozu.member.service.IUmsMemberService;
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
@RequestMapping("/member")
public class UmsMemberController extends BaseController {
    @Autowired
    private IUmsMemberService umsMemberService;

    @Autowired
    private IUmsMemberRoleService umsMemberRoleService;

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("member:member:list")
    @GetMapping("/list")
    public TableDataInfo list(UmsMember umsMember) {
        startPage();
        List<UmsMember> list = umsMemberService.selectUmsMemberList(umsMember);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("member:member:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UmsMember umsMember) {
        List<UmsMember> list = umsMemberService.selectUmsMemberList(umsMember);
        ExcelUtil<UmsMember> util = new ExcelUtil<UmsMember>(UmsMember.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @RequiresPermissions("member:member:query")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return success(umsMemberService.selectUmsMemberByUserId(userId));
    }

    /**
     * 新增用户信息
     */
    @RequiresPermissions("member:member:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsMember umsMember) {
        return toAjax(umsMemberService.insertUmsMember(umsMember));
    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("member:member:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMember umsMember) {
        return toAjax(umsMemberService.updateUmsMember(umsMember));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("member:member:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(umsMemberService.deleteUmsMemberByUserIds(userIds));
    }

    @GetMapping("/info/account")
    R<LoginUser> getMemberInfo(@RequestParam("account") String account) {
        UmsMember member = umsMemberService.getMemberByAccount(account);
        if (StringUtils.isNull(member)) {
            return R.fail("用户名或密码错误");
        }
        LoginUser memberVo = umsMemberService.convertToLoginUser(member);
        return R.ok(memberVo);
    }

    @GetMapping("/info/social")
    R<LoginUser> getMemberInfo(@RequestParam("socialCode") String socialCode, @RequestParam("type") SocialTypeEnum type) {
        UmsMember member = umsMemberService.getMemberBySocialCode(socialCode, type);
        if (StringUtils.isNull(member)) {
            return R.fail("未找到社交用户信息");
        }
        LoginUser memberVo = umsMemberService.convertToLoginUser(member);
        return R.ok(memberVo);
    }

    @GetMapping("/infoOrRegister/{phone}")
    R<LoginUser> getMemberInfoOrRegister(@PathVariable("phone") String phone) {
        LoginUser memberVo = umsMemberService.getMemberOrRegister(phone);
        return R.ok(memberVo);
    }

    @PostMapping("/register/email")
    R<LoginUser> registerMemberInfoByEmail(@RequestBody UmsMember umsMember) {
        LoginUser loginUser;
        try {
            loginUser = umsMemberService.registerMemberByEmail(umsMember);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.ok(loginUser);
    }

    @PostMapping("/register/social")
    R<LoginUser> registerMemberInfoBySocial(@RequestBody UmsMemberDTO umsMemberDTO) {
        LoginUser loginUser;
        try {
            loginUser = umsMemberService.registerMemberBySocial(umsMemberDTO);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.ok(loginUser);
    }

}
