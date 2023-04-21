package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.common.core.model.LoginUser;
import org.springframework.stereotype.Component;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 22:09
 */
@Component
public class MemberLoginEmailService implements ILoginService {

    @Override
    public LoginUser login(LoginBody form) {

        // TODO 发送请求 目标接口直接返回 LoginUser类型对象

        // 只有两种情况 账号或者密码错误  成功


        return null;
    }

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.MEMBER_USER_EMAIL;
    }
}
