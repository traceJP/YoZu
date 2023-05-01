package com.tracejp.yozu.auth.form;

import com.tracejp.yozu.member.api.enums.SocialTypeEnum;
import lombok.Data;

/**
 * <p> 社交登录参数实体 <p/>
 *
 * @author traceJP
 * @since 2023/4/28 8:26
 */
@Data
public class LoginSocialBody {

    /**
     * 社交登录回调授权码
     */
    private String code;

    /**
     * 社交登录平台类型
     */
    private SocialTypeEnum type;

}
