package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.domain.SocialTokenResult;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;

/**
 * <p> 注册用户 社交登录服务接口 <p/>
 *
 * @author traceJP
 * @since 2023/4/28 8:15
 */
public interface ISocialLoginService {

    /**
     * 获取社交平台token
     * @param code 社交平台授权码
     * @return 社交平台token
     */
    SocialTokenResult getSocialToken(String code);

    /**
     *  TODO 拿到用户平台信息需要同时使用token + uuid 获取
     * 获取社交平台用户信息
     * @param token 社交平台token
     * @return 社交平台用户信息
     */
    String getSocialUserInfo(String token, String uuid);

    /**
     * 将社交平台用户信息转换为本系统用户实体
     * @param socialData 社交平台用户信息
     * @return 本系统用户实体
     */
    UmsMember convertToUmsMember(String socialData);

    /**
     * 获取登录类型
     */
    SocialTypeEnum getSocialType();

}
