package com.ruoyi.auth.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 微信用户信息实体
 */
@Data
public class WxUserInfo {

    /**
     * 用户的唯一标识
     */
    @NotBlank
    @JsonProperty("openid")
    private String openId;

    /**
     * 用户昵称
     */
    @NotBlank
    @JsonProperty("nickname")
    private String nickname;

    /**
     * 用户性别 (1-男, 2-女, 0-未知)
     */
    @JsonProperty("sex")
    private Integer gender;

    /**
     * 用户所在省份
     */
    @JsonProperty("province")
    private String province;

    /**
     * 用户所在城市
     */
    @JsonProperty("city")
    private String city;

    /**
     * 用户所在国家
     */
    @JsonProperty("country")
    private String country;

    /**
     * 用户头像URL
     */
    @JsonProperty("headimgurl")
    private String avatarUrl;

    /**
     * 用户特权信息
     */
    @JsonProperty("privilege")
    private List<String> privileges;

    /**
     * 用户在开放平台的唯一标识符
     */
    @JsonProperty("unionid")
    private String unionId;

    /**
     * 性别枚举（增强可读性）
     */
    public enum Gender {
        UNKNOWN(0, "未知"),
        MALE(1, "男"),
        FEMALE(2, "女");

        private final int code;
        private final String desc;

        Gender(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Gender fromCode(int code) {
            for (Gender value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            return UNKNOWN;
        }
    }

    /**
     * 获取性别枚举（业务方法）
     */
    public Gender getGenderEnum() {
        return Gender.fromCode(this.gender);
    }
}