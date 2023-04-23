package com.tracejp.yozu.member.api.factory;

import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/21 22:27
 */
public class RemoteMemberFallbackFactory implements RemoteMemberService {

    @Override
    public R<LoginUser> getMemberInfo(String account, String source) {
        return null;
    }

    @Override
    public R<LoginUser> getMemberInfoOrRegister(String phone, String source) {
        return null;
    }

    @Override
    public R<Boolean> registerMemberInfo(UmsMember umsMember, String source) {
        return null;
    }

}
