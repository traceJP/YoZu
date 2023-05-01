package com.tracejp.yozu.auth.service;

import com.tracejp.yozu.auth.domain.SocialTokenResult;
import com.tracejp.yozu.auth.form.LoginSocialBody;
import com.tracejp.yozu.common.core.constant.SecurityConstants;
import com.tracejp.yozu.common.core.domain.R;
import com.tracejp.yozu.common.core.exception.ServiceException;
import com.tracejp.yozu.common.core.exception.auth.LoginOperationException;
import com.tracejp.yozu.common.core.exception.auth.RepeatTypeException;
import com.tracejp.yozu.common.core.model.LoginUser;
import com.tracejp.yozu.member.api.RemoteMemberService;
import com.tracejp.yozu.member.api.domain.UmsMember;
import com.tracejp.yozu.member.api.domain.dto.UmsMemberDTO;
import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> 社交登录处理器聚合类（策略处理器） <p/>
 *
 * @author traceJP
 * @since 2023/4/28 8:56
 */
@Slf4j
@Component
public class UmsSocialLoginServices {

    @Autowired
    private RemoteMemberService remoteMemberService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 社交服务处理器收集
     */
    private final ConcurrentHashMap<SocialTypeEnum, ISocialLoginService> socialLoginServices =
            new ConcurrentHashMap<>(16);

    public UmsSocialLoginServices(ApplicationContext applicationContext) {
        addSocialLoginServices(applicationContext.getBeansOfType(ISocialLoginService.class).values());
        log.info("Registered {} social login service(s)", socialLoginServices.size());
    }

    public LoginUser login(LoginSocialBody form) throws ExecutionException, InterruptedException {
        ISocialLoginService service = getSupportedType(form.getType());

        SocialTokenResult tokenResult = service.getSocialToken(form.getCode());

        // 异步请求社交平台用户信息
        CompletableFuture<String> socialUserInfoCompletableFuture = CompletableFuture.supplyAsync(
                () -> service.getSocialUserInfo(tokenResult.getToken(), tokenResult.getUuid()),
                threadPoolExecutor
        );

        // 异步请求本系统用户信息
        CompletableFuture<LoginUser> loginUserCompletableFuture =
                // 尝试获取本系统用户信息
                CompletableFuture.supplyAsync(() -> remoteMemberService.getMemberInfo(tokenResult.getUuid(),
                                form.getType(), SecurityConstants.INNER), threadPoolExecutor)
                        .thenApply(memberInfo -> {
                            if (R.FAIL == memberInfo.getCode() || memberInfo.getData() == null) {
                                // 等待社交平台用户信息进行注册
                                String socialUserInfo = socialUserInfoCompletableFuture.join();
                                UmsMember member = service.convertToUmsMember(socialUserInfo);
                                UmsMemberDTO memberDTO = new UmsMemberDTO().convertFrom(member);
                                memberDTO.setSocialUid(tokenResult.getUuid());
                                memberDTO.setSocialType(form.getType());

                                // TODO 通过 feign 调用传递string的json值时会被压缩导致强制解析
                                //memberDTO.setSocialData(socialUserInfo);

                                R<LoginUser> loginUser = remoteMemberService.registerMemberInfo(memberDTO, SecurityConstants.INNER);
                                if (R.FAIL == loginUser.getCode() || loginUser.getData() == null) {
                                    throw new ServiceException("社交登录注册异常");
                                }

                                return loginUser.getData();
                            }

                            return memberInfo.getData();
                        });

        // 等待注册或寻找到本系统用户信息
        return loginUserCompletableFuture.get();
    }

    private void addSocialLoginServices(Collection<ISocialLoginService> services) {
        if (!CollectionUtils.isEmpty(services)) {
            for (ISocialLoginService service : services) {
                if (this.socialLoginServices.containsKey(service.getSocialType())) {
                    throw new RepeatTypeException("Same social type implements must be unique");
                }
                this.socialLoginServices.put(service.getSocialType(), service);
            }
        }
    }

    private ISocialLoginService getSupportedType(SocialTypeEnum type) {
        ISocialLoginService handler =
                socialLoginServices.get(type);
        if (handler == null) {
            throw new LoginOperationException("No available social services to operate the login operation");
        }
        return handler;
    }

}
