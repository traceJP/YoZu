package com.tracejp.yozu.auth.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/23 15:46
 */
@Data
public class LoginAccountBody {

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

}
