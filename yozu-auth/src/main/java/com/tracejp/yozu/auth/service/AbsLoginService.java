package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.security.utils.SecurityUtils;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/23 15:50
 */
public abstract class AbsLoginService {

    protected void matchesPassword(String rawPassword, String encodedPassword) {
        boolean matchesPassword = SecurityUtils.matchesPassword(rawPassword, encodedPassword);
        if (!matchesPassword) {
            throw new ServiceException("用户名或密码错误");
        }
    }

}
