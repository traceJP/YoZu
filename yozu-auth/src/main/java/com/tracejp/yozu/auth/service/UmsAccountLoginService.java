package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/22 18:53
 */
@Component
public class UmsAccountLoginService implements ILoginService {

    @Autowired
    private RemoteMemberService remoteMemberService;

    @Override
    public LoginUser login(LoginBody form) {
        String account = null;
        if (StringUtils.isNotEmpty(form.getPhone())) {
            account = form.getPhone();
        } else if (StringUtils.isNotEmpty(form.getEmail())) {
            account = form.getEmail();
        } else {
            throw new ServiceException("用户名或密码错误");
        }

        R<LoginUser> memberResult = remoteMemberService.getMemberInfo(account, SecurityConstants.INNER);
        if (StringUtils.isNull(memberResult) || StringUtils.isNull(memberResult.getData())) {
            throw new ServiceException("用户名或密码错误");
        }
        if (R.FAIL == memberResult.getCode()) {
            throw new ServiceException(memberResult.getMsg());
        }

        LoginUser userInfo = memberResult.getData();
        UmsMember memberInfo = userInfo.getUserInfo(UmsMember.class);

        // 密码比对
        matchesPassword(form.getPassword(), memberInfo.getPassword());

        return userInfo;
    }

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.MEMBER_USER_ACCOUNT;
    }

}
