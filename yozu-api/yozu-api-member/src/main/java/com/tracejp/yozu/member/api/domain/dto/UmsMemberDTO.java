package com.tracejp.yozu.member.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.tracejp.yozu.common.core.web.domain.IInputConverter;
import com.tracejp.yozu.common.core.web.domain.IOutputConverter;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/30 15:35
 */
@Data
public class UmsMemberDTO
        implements IInputConverter<UmsMember>, IOutputConverter<UmsMemberDTO, UmsMember> {

    /**
     * 用户账号【uuid】
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

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

    // ====================================== DTO ===================================

    /**
     * 社交平台唯一标识
     */
    private String socialUid;

    /**
     * 社交平台类型
     */
    private SocialTypeEnum socialType;

    /**
     * 社交数据【Json】
     */
    @JsonRawValue
    private String socialData;

}
