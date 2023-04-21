package com.tracejp.yozu.member.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 用户信息对象 ums_member
 *
 * @author tracejp
 * @date 2023-04-21
 */
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
     * 邮箱是否被验证（0未验证
     */
    @Excel(name = "邮箱是否被验证", readConverterExp = "邮箱是否被验证（0未验证")
    private Integer emailChecked;

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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmailChecked(Integer emailChecked) {
        this.emailChecked = emailChecked;
    }

    public Integer getEmailChecked() {
        return emailChecked;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("roleId", getRoleId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("emailChecked", getEmailChecked())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("birthday", getBirthday())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
