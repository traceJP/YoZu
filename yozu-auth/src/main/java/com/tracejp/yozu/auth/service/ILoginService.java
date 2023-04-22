package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.security.utils.SecurityUtils;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 8:59
 */
public interface ILoginService {

    LoginUser login(LoginBody form);

    LoginTypeEnum getLoginType();
    // $2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2  admin123
    default void matchesPassword(String rawPassword, String encodedPassword) {
        boolean matchesPassword = SecurityUtils.matchesPassword(rawPassword, encodedPassword);
        if (!matchesPassword) {
            throw new ServiceException("用户名或密码错误");
        }
    }

}
