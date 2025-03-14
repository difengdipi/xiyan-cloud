package com.ruoyi.auth.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @JsonProperty("id")
    private Long userId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户名
     */
    @JsonProperty("account")
    private String userName;

    /**
     * 昵称
     */
    @JsonProperty("nickname")
    private String nickName;

    /**
     * 手机号
     */
    @JsonProperty("mobile")
    private String phonenumber;

    /**
     * 登录凭证
     */
    private String token;

}
