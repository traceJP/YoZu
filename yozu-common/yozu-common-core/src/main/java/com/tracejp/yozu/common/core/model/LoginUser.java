package com.tracejp.yozu.common.core.model;

import com.alibaba.fastjson2.JSON;
import com.tracejp.yozu.common.core.enums.UserType;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 *
 * @author yozu
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id [ 系统用户：自增ID | 会员用户：自增ID ]
     */
    private Long userid;

    /**
     * 用户名 [ 系统用户：非重复用户名 | 会员注册用户：UUID ]
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户信息
     */
    private Object userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Object getUserInfo() {
        return userInfo;
    }

    /**
     * 注意：该方法使用 JSON 进行序列化，属于深拷贝
     */
    public <T> T getUserInfo(Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(userInfo), clazz);
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

}
