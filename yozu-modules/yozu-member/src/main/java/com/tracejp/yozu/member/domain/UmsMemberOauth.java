package com.tracejp.yozu.member.domain;

import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户信息对象 ums_member_oauth
 *
 * @author tracejp
 * @date 2023-04-21
 */
@Data
public class UmsMemberOauth extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 社交账号唯一UID
     */
    @Excel(name = "社交账号唯一UID")
    private String socialId;

    /**
     * 社交账号类型[0->QQ,
     */
    @Excel(name = "社交账号类型[0->QQ,")
    private String socialType;

    /**
     * 社交数据【Json】
     */
    @Excel(name = "社交数据【Json】")
    private String socialData;

}
