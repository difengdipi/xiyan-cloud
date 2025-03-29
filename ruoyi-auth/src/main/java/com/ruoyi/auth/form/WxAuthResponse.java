package com.ruoyi.auth.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 微信登录凭证响应实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WxAuthResponse {

    /**
     * 微信会话密钥（需服务端保存，不能传给客户端！）
     * 用于解密用户加密数据
     */
    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * 用户唯一标识（同一小程序下唯一）
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 错误码（成功时为null）
     */
    @JsonProperty("errcode")
    private Integer errCode;

    /**
     * 错误信息（成功时为null）
     */
    @JsonProperty("errmsg")
    private String errMsg;
}