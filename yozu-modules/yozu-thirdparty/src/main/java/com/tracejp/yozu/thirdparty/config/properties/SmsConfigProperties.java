package com.tracejp.yozu.thirdparty.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> 短信配置 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 22:15
 */
@Data
@ConfigurationProperties(prefix = "yozu.sms")
public class SmsConfigProperties {

    /**
     * 配置节点
     */
    private String endpoint;

    /**
     * key
     */
    private String accessKeyId;

    /**
     * 密匙
     */
    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信应用ID (腾讯专属)
     */
    private String sdkAppId;

}
