package com.tracejp.yozu.auth.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册对象
 *
 * @author yozu
 */
@Data
public class RegisterBody {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "密码强度不够")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

}
