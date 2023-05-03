package com.tracejp.yozu.thirdparty.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> minio配置 <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:25
 */
@Data
@ConfigurationProperties(prefix = "yozu.file")
public class FileConfigProperties {

    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

}
