package com.tracejp.yozu.thirdparty.config;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.tracejp.yozu.thirdparty.config.properties.SmsConfigProperties;
import com.tracejp.yozu.thirdparty.constant.SmsHandlerType;
import com.tracejp.yozu.thirdparty.handler.sms.AliSmsHandler;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 短信配置类 <p/>
 *
 * @author traceJP
 * @since 2023/4/25 23:01
 */
@Configuration
@EnableConfigurationProperties(SmsConfigProperties.class)
public class SmsConfig {

    @Configuration
    @ConditionalOnProperty(value = "yozu.sms.handler", havingValue = SmsHandlerType.ALIYUN)
    @ConditionalOnClass({AliSmsHandler.class, AsyncClient.class})
    static class AliSmsConfig {

        @Bean
        public AsyncClient smsClient(SmsConfigProperties properties) {
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId(properties.getAccessKeyId())
                    .accessKeySecret(properties.getAccessKeySecret())
                    .build());
            return AsyncClient.builder()
                    .credentialsProvider(provider)
                    .overrideConfiguration(ClientOverrideConfiguration.create().setEndpointOverride(properties.getEndpoint()))
                    .build();
        }

        @Bean
        public AliSmsHandler aliSmsHandler(AsyncClient smsClient, SmsConfigProperties properties) {
            return new AliSmsHandler(smsClient, properties.getSignName());
        }

    }


}
