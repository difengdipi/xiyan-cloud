package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信OAuth2授权返回结果
 */
@Data
public class WxOAuth2Result {
    
    /**
     * 接口调用凭证
     */
    @JsonProperty("access_token")
    private String accessToken;
    
    /**
     * access_token 有效期（秒）
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
    
    /**
     * 刷新凭证（可用来续期）
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    /**
     * 用户唯一标识
     */
    @JsonProperty("openid")
    private String openId;
    
    /**
     * 授权作用域（多个用逗号分隔）
     */
    @JsonProperty("scope")
    private String scope;
    
    /**
     * 多账号统一标识（需微信开放平台绑定）
     */
    @JsonProperty("unionid")
    private String unionId;
}