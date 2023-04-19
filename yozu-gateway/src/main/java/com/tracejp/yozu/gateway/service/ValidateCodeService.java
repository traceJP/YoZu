package com.tracejp.yozu.gateway.service;

import java.io.IOException;

import com.tracejp.yozu.common.core.exception.CaptchaException;
import com.tracejp.yozu.common.core.web.domain.AjaxResult;

/**
 * 验证码处理
 *
 * @author yozu
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     */
    AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    void checkCaptcha(String key, String value) throws CaptchaException;
}
