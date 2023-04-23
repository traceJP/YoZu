package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.LoginAccountBody;
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
 * <p> 注册用户登录校验方法 <p/>
 *
 * @author traceJP
 * @since 2023/4/23 15:48
 */
@Component
public class UmsLoginService extends AbsLoginService {

    @Autowired
    private RemoteMemberService remoteMemberService;


    public LoginUser loginByAccount(LoginAccountBody form) {

        R<LoginUser> memberResult = remoteMemberService.getMemberInfo(form.getAccount(), SecurityConstants.INNER);
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

}
