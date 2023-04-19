package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.system.api.model.LoginUser;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 22:09
 */
public class MemberLoginEmailService implements ILoginService {

    @Override
    public LoginUser login(LoginBody form) {


        return null;
    }

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.MEMBER_USER_EMAIL;
    }
}
