package com.tracejp.yozu.auth.handler;

import com.tracejp.yozu.auth.form.LoginBody;
import com.tracejp.yozu.auth.form.constant.LoginTypeEnum;
import com.tracejp.yozu.auth.service.ILoginService;
import com.tracejp.yozu.common.core.exception.auth.LoginOperationException;
import com.tracejp.yozu.common.core.exception.auth.RepeatTypeException;
import com.tracejp.yozu.common.core.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 9:07
 */
@Slf4j
@Component
public class LoginHandlerContext {

    ConcurrentHashMap<LoginTypeEnum, ILoginService> loginServices = new ConcurrentHashMap<>(16);

    public LoginHandlerContext(ApplicationContext applicationContext) {
        // Add all login service
        addLoginServices(applicationContext.getBeansOfType(ILoginService.class).values());
        log.info("Registered {} login service(s)", loginServices.size());
    }

    public LoginUser login(LoginBody param, LoginTypeEnum type) {
        return getSupportedType(type).login(param);
    }

    private void addLoginServices(Collection<ILoginService> loginServices) {
        if (!CollectionUtils.isEmpty(loginServices)) {
            for (ILoginService service : loginServices) {
                if (this.loginServices.containsKey(service.getLoginType())) {
                    throw new RepeatTypeException("Same attachment type implements must be unique");
                }
                this.loginServices.put(service.getLoginType(), service);
            }
        }
    }

    private ILoginService getSupportedType(LoginTypeEnum type) {
        ILoginService handler = loginServices.getOrDefault(type, loginServices.get(LoginTypeEnum.SYSTEM_USER));
        if (handler == null) {
            throw new LoginOperationException("No available login handlers to operate the request");
        }
        return handler;
    }

}
