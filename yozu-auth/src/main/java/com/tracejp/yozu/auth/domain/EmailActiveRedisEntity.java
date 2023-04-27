package com.tracejp.yozu.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 邮件激活RedisEntity <p/>
 *
 * @author traceJP
 * @since 2023/4/26 17:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailActiveRedisEntity {

    /**
     * 邮箱激活链接UUID
     */
    private String code;

    /**
     * 激活标志位
     */
    private Boolean active;

    /**
     * 邮件发送时间（时间戳）
     */
    private Long sendTime;

}
