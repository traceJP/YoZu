package com.tracejp.yozu.member.api;

import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.constant.ServiceNameConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.domain.dto.UmsMemberDTO;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
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
    @GetMapping("/member/info/account")
    R<LoginUser> getMemberInfo(@RequestParam("account") String account,
                               @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    /**
     * 获取社交平台用户信息
     * @param socialCode 社交唯一uuid
     * @param type 社交平台枚举
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/member/info/social")
    R<LoginUser> getMemberInfo(@RequestParam("socialCode") String socialCode,
                               @RequestParam("type") SocialTypeEnum type,
                               @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    /**
     * 通过手机号获取用户信息，如果不存在就注册
     * @param phone 手机号
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/member/infoOrRegister/{phone}")
    R<LoginUser> getMemberInfoOrRegister(@PathVariable("phone") String phone,
                                         @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    /**
     * 注册用户信息 通过邮箱
     *
     * @param umsMember 用户信息 邮箱 密码
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/member/register/email")
    R<LoginUser> registerMemberInfo(@RequestBody UmsMember umsMember,
                                  @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    /**
     * 注册用户信息 通过社交平台uuid
     *
     * @param umsMember 用户信息
     * @param source  请求来源
     * @return 结果
     */
    // TODO 这里需要测试 加上json格式不会被 gzip 压缩
    @PostMapping(value = "/member/register/social", consumes = MediaType.APPLICATION_JSON_VALUE)
    R<LoginUser> registerMemberInfo(@RequestBody UmsMemberDTO umsMember,
                                  @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

}
