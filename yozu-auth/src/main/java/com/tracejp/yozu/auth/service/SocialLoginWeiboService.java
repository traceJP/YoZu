package com.tracejp.yozu.auth.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tracejp.yozu.auth.domain.SocialTokenResult;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 微博社交登录 <p/>
 *
 * @author traceJP
 * @since 2023/4/28 9:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "social.weibo")
public class SocialLoginWeiboService implements ISocialLoginService {

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String redirectUri;

    private static final String TOKEN_URL = "https://api.weibo.com/oauth2/access_token";

    private static final String USERINFO_URL = "https://api.weibo.com/2/users/show.json";

    @Override
    public SocialTokenResult getSocialToken(String code) {
        // 封装请求参数
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("client_id", clientId);
        requestParam.put("client_secret", clientSecret);
        requestParam.put("grant_type", grantType);
        requestParam.put("redirect_uri", redirectUri);
        requestParam.put("code", code);

        // 发送请求到微博服务器
        String resultJson = HttpUtil.post(TOKEN_URL, requestParam);
        if (StringUtils.isEmpty(resultJson)) {
            throw new ServiceException("微博服务器异常");
        }

        // 解析 & 返回结果
        JSONObject resultObject = JSON.parseObject(resultJson);
        String accessToken = (String) resultObject.get("access_token");
        Integer expiresIn = (Integer) resultObject.get("expires_in");
        String uid = (String) resultObject.get("uid");
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(uid)) {
            throw new ServiceException("code码错误");
        }

        return new SocialTokenResult(accessToken, expiresIn, uid);
    }

    @Override
    public String getSocialUserInfo(String token, String uuid) {
        // 封装请求参数
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("access_token", token);
        requestParam.put("uid", uuid);

        // 发送请求到微博服务器
        String resultJson = HttpUtil.get(USERINFO_URL, requestParam);
        if (StringUtils.isEmpty(resultJson)) {
            throw new ServiceException("微博服务器异常");
        }

        return resultJson;
    }

    @Override
    public UmsMember convertToUmsMember(String socialData) {
        JSONObject jsonObject = JSON.parseObject(socialData);
        UmsMember member = new UmsMember();
        member.setNickName(jsonObject.getString("name"));
        member.setSex(StringUtils.equals(jsonObject.getString("gender"), "m") ? "1" : "2");
        return member;
    }

    @Override
    public SocialTypeEnum getSocialType() {
        return SocialTypeEnum.WEIBO;
    }

}
