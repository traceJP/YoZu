package com.tracejp.yozu.common.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 *
 * @author yozu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableYozuFeignClients {
    String[] value() default {};

    String[] basePackages() default {"com.tracejp.yozu"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
