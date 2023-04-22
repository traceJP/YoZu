package com.tracejp.yozu.member.api;

import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.constant.ServiceNameConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.domain.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 *
 * @author yozu
 */
@FeignClient(contextId = "remoteMemberService", value = ServiceNameConstants.MEMBER_SERVICE)
public interface RemoteMemberService {
    /**
     * 通过账号查询用户信息
     *
     * @param account 账号（邮箱 手机号）
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/member/info/{account}")
    R<LoginUser> getMemberInfo(@PathVariable("account") String account, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    // TODO 短信登录接口   getMemberInfoOrRegister
    // 短信登录 先拿到手机号，1 检查是否存在   2检查验证码是否匹配
    // 1 发送验证码
    // 2 用户拿到验证码， 提交手机号和验证码
    // 3 auth服务检查验证码和手机号redis匹配
    // 4 如果匹配 就去member服务拿用户信息，如果不存在就创建用户

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
