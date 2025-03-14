package com.ruoyi.auth.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 小程序简单登录参数
 */
@Data
public class WxMinSimpleLoginDTO {
    
    /** 手机号 */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phoneNumber;
}