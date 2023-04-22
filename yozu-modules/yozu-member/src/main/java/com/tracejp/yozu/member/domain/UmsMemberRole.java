package com.tracejp.yozu.member.domain;

import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户信息对象 ums_member_role
 *
 * @author tracejp
 * @date 2023-04-21
 */
@Data
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

}
