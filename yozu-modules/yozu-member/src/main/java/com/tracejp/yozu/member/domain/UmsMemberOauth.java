package com.tracejp.yozu.member.domain;

import com.tracejp.yozu.common.core.annotation.Excel;
import com.tracejp.yozu.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户信息对象 ums_member_oauth
 *
 * @author tracejp
 * @date 2023-04-21
 */
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialData(String socialData) {
        this.socialData = socialData;
    }

    public String getSocialData() {
        return socialData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("socialId", getSocialId())
                .append("socialType", getSocialType())
                .append("socialData", getSocialData())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
