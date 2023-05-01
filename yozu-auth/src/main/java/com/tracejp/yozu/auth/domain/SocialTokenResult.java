package com.tracejp.yozu.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 社交登录令牌返回实体 <p/>
 *
 * @author traceJP
 * @since 2023/4/29 20:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialTokenResult {

    /**
     * token
     */
    private String token;

    /**
     * token过期时间
     */
    private Integer expiresIn;

    /**
     * 社交平台唯一UUID
     */
    private String uuid;

}
