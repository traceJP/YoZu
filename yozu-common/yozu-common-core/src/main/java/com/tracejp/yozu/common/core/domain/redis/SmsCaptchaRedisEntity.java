package com.tracejp.yozu.common.core.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 短信验证码RedisEntity <p/>
 *
 * @author traceJP
 * @since 2023/4/27 20:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCaptchaRedisEntity {

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送时间
     */
    private Long sendTime;

}
