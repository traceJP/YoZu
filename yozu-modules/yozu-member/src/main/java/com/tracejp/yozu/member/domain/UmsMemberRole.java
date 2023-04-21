package com.tracejp.yozu.member.domain;

import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户信息对象 ums_member_role
 *
 * @author tracejp
 * @date 2023-04-21
 */
public class UmsMemberRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名
     */
    @Excel(name = "角色名")
    private String roleName;

    /**
     * 角色权限标识
     */
    @Excel(name = "角色权限标识")
    private String roleKey;

    /**
     * 是否为默认等级[0->不是；1->是]
     */
    @Excel(name = "是否为默认等级[0->不是；1->是]")
    private Integer defaultStatus;

    /**
     * 角色状态（0代表存在
     */
    @Excel(name = "角色状态", readConverterExp = "角色状态（0代表存在")
    private String status;

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roleId", getRoleId())
                .append("roleName", getRoleName())
                .append("roleKey", getRoleKey())
                .append("defaultStatus", getDefaultStatus())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
