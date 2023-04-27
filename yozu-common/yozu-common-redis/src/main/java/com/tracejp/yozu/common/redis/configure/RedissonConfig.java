package com.tracejp.yozu.common.redis.configure;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p> Redisson配置 <p/>
 *
 * @author traceJP
 * @since 2023/4/26 8:16
 */
@AutoConfiguration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost = "localhost";

    @Value("${spring.redis.port}")
    private String redisPort = "6379";

    @Value("${spring.redis.password}")
    private String redisPassword = "123456";

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword);
        return Redisson.create(config);
    }

}
