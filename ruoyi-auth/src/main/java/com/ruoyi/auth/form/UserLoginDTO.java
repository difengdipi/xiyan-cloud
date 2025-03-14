package com.ruoyi.auth.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {

    /** 小程序code */
    @NotEmpty(message = "code不能为空")
    private String code;

    /** 包括敏感数据在内的完整用户信息的加密数据 */
    private String encryptedData;

    /** 加密算法的初始向量 */
    private String iv;

}
