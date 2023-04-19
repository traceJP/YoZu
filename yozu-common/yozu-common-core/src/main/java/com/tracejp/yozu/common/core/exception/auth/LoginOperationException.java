package com.tracejp.yozu.common.core.exception.auth;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/19 21:59
 */
public class LoginOperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginOperationException(String message) {
        super(message);
    }

}
