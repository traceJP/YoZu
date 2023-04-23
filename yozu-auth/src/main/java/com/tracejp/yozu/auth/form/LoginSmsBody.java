package com.tracejp.yozu.auth.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/4/23 19:08
 */
@Data
public class LoginSmsBody {

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;

}
