package com.tracejp.yozu.member.api;

import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.constant.ServiceNameConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.factory.RemoteMemberFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 *
 * @author yozu
 */
@FeignClient(contextId = "remoteMemberService", value = ServiceNameConstants.MEMBER_SERVICE, fallbackFactory = RemoteMemberFallbackFactory.class)
public interface RemoteMemberService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/member/info/{username}")
    R<LoginUser> getMemberInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param umsMember 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    R<Boolean> registerUserInfo(@RequestBody UmsMember umsMember, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
