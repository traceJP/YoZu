package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.system.api.model.LoginUser;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 8:59
 */
public interface ILoginService {

    public LoginUser login(LoginBody form);

    public LoginTypeEnum getLoginType();

}
