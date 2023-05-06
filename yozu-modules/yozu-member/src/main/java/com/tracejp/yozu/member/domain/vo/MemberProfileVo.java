package com.tracejp.yozu.member.domain.vo;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracejp.yozu.member.domain.UmsMemberRole;
import lombok.Data;

import java.util.Date;

/**
 * <p> 用户信息视图 <p/>
 *
 * @author traceJP
 * @since 2023/5/5 8:41
 */
@Data
public class MemberProfileVo {

    /**
     * 角色信息
     */
    private UmsMemberRole role;

    /**
     * 用户账号【uuid】
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0男
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 帐号状态（0正常
     */
    private String status;

    /**
     * 设置（脱敏）邮箱
     */
    public void setEmailDesensitized(String email) {
        this.email = DesensitizedUtil.email(email);
    }

    /**
     * 设置（脱敏）手机号码
     */
    public void setPhonenumberDesensitized(String phonenumber) {
        this.phonenumber = DesensitizedUtil.mobilePhone(phonenumber);
    }

}
