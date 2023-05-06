package com.tracejp.yozu.member.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracejp.yozu.common.core.web.domain.IInputConverter;
import com.tracejp.yozu.member.api.domain.UmsMember;

import java.util.Date;

/**
 * <p> 用户信息可配置参数 <p/>
 *
 * @author traceJP
 * @since 2023/5/5 9:09
 */
public class MemberProfileParam implements IInputConverter<UmsMember> {

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

}
