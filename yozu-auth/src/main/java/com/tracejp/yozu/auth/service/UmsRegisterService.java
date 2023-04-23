package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.form.RegisterBody;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.utils.StringUtils;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p> 会员注册服务 <p/>
 *
 * @author traceJP
 * @since 2023/4/23 20:27
 */
@Component
public class UmsRegisterService {

    @Autowired
    private RemoteMemberService remoteMemberService;

    public void register(RegisterBody form) {

        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            throw new ServiceException("两次密码不一致");
        }
        // 密码强度检查 => 数字 + 字母
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!form.getPassword().matches(regex)) {
            throw new ServiceException("密码强度不够");
        }

        UmsMember member = new UmsMember();
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        R<Boolean> register = remoteMemberService.registerMemberInfo(member, SecurityConstants.INNER);
        if (R.FAIL == register.getCode()) {
            throw new ServiceException("注册失败");
        }
    }

}
