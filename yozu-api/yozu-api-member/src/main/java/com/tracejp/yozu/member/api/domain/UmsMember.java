package com.tracejp.yozu.member.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息对象 ums_member
 *
 * @author tracejp
 * @date 2023-04-21
 */
@Data
public class UmsMember extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 注册用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    @Excel(name = "角色ID")
    private Long roleId;

    /**
     * 用户账号【uuid】
     */
    @Excel(name = "用户账号【uuid】")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phonenumber;

    /**
     * 用户性别（0男
     */
    @Excel(name = "用户性别", readConverterExp = "用户性别（0男")
    private String sex;

    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 帐号状态（0正常
     */
    @Excel(name = "帐号状态", readConverterExp = "帐号状态（0正常")
    private String status;

    /**
     * 删除标志（0代表存在
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

}
