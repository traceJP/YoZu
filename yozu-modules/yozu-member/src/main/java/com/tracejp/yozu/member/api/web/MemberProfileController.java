package com.tracejp.yozu.member.api.web;

import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.common.core.utils.bean.BeanUtils;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;
import com.tracejp.yozu.common.security.annotation.RequiresLogin;
import com.tracejp.yozu.common.security.utils.SecurityUtils;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import com.tracejp.yozu.member.domain.param.MemberProfileParam;
import com.tracejp.yozu.member.domain.vo.MemberProfileVo;
import com.tracejp.yozu.member.service.IUmsMemberRoleService;
import com.tracejp.yozu.member.service.IUmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.tracejp.yozu.common.core.web.domain.AjaxResult.error;
import static com.tracejp.yozu.common.core.web.domain.AjaxResult.success;

/**
 * <p> 个人信息接口 <p/>
 *
 * @author traceJP
 * @since 2023/5/4 18:56
 */
@RestController
@RequestMapping("/web/member/profile")
public class MemberProfileController {

    @Autowired
    private IUmsMemberService memberService;

    @Autowired
    private IUmsMemberRoleService memberRoleService;


    /**
     * 个人信息
     * @return MemberProfileVo
     */
    @RequiresLogin
    @GetMapping
    public AjaxResult profile() {
        Long userid = SecurityUtils.getLoginUser().getUserid();
        UmsMember member = memberService.selectUmsMemberByUserId(userid);
        UmsMemberRole role = memberRoleService.selectUmsMemberRoleByRoleId(member.getRoleId());
        MemberProfileVo vo = new MemberProfileVo();
        BeanUtils.copyProperties(member, vo);
        vo.setRole(role);
        vo.setEmailDesensitized(member.getEmail());
        vo.setPhonenumberDesensitized(member.getPhonenumber());
        return success(vo);
    }

    /**
     * 信息修改
     * @param param MemberProfileParam
     */
    @RequiresLogin
    @PutMapping
    public AjaxResult updateProfile(@RequestBody MemberProfileParam param) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UmsMember update = param.convertTo();
        update.setUserId(loginUser.getUserid());
        if (memberService.updateUmsMember(update) > 0) {
            memberService.updateCurrentMemberLoginCache(update);
            return success();
        }
        return error("修改个人信息异常，请联系管理员");
    }

    /**
     * 修改密码
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    @RequiresLogin
    @PutMapping("/update/pwd")
    public AjaxResult updatePassword(String oldPassword, String newPassword) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UmsMember userInfo = loginUser.getUserInfo(UmsMember.class);
        String password = userInfo.getPassword();
        if (!StringUtils.isEmpty(password)) {  // 有密码才校验
            if (!SecurityUtils.matchesPassword(oldPassword, password)) {
                return error("修改密码失败，旧密码错误");
            }
            if (SecurityUtils.matchesPassword(newPassword, password)) {
                return error("新密码不能与旧密码相同");
            }
        }
        // 修改密码
        userInfo.setPassword(SecurityUtils.encryptPassword(newPassword));
        if (memberService.updateUmsMember(userInfo) > 0) {
            // 更新缓存用户密码
            memberService.updateCurrentMemberLoginCache(userInfo);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    // 3、手机号修改
    // 4、邮箱修改


}
