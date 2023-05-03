package com.tracejp.yozu.thirdparty.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.tracejp.yozu.common.redis.service.RedisService;
import com.tracejp.yozu.thirdparty.config.properties.FileConfigProperties;
import com.tracejp.yozu.thirdparty.constant.FileHandlerType;
import com.tracejp.yozu.thirdparty.handler.file.MinioFileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> minio config <p/>
 *
 * @author traceJP
 * @since 2023/5/2 13:24
 */
@Slf4j
@Configuration
public class FileConfig {

    @Configuration
    @EnableConfigurationProperties(FileConfigProperties.class)
    @ConditionalOnProperty(value = "yozu.file.handler", havingValue = FileHandlerType.MINIO)
    @ConditionalOnClass(MinioFileHandler.class)
    static class MinioFileConfig {

        @Bean
        public AmazonS3 amazonS3Client(FileConfigProperties properties) {
            ClientConfiguration config = new ClientConfiguration();
            config.setProtocol(Protocol.HTTP);
            config.setConnectionTimeout(5000);
            config.setUseExpectContinue(true);
            AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
            AwsClientBuilder.EndpointConfiguration endPoint =
                    new AwsClientBuilder.EndpointConfiguration(properties.getUrl(), Regions.CN_NORTH_1.name());

            return AmazonS3ClientBuilder.standard()
                    .withClientConfiguration(config)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(endPoint)
                    .withPathStyleAccessEnabled(true).build();
        }

        @Bean
        public MinioFileHandler minioFileHandler(AmazonS3 client,
                                                 RedisService redisService,
                                                 FileConfigProperties properties) {
            return new MinioFileHandler(client, redisService, properties.getUrl());
        }

    }

}
