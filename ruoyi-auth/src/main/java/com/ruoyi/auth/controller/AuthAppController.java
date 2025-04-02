package com.ruoyi.auth.controller;

import com.ruoyi.auth.form.LoginBody;
import com.ruoyi.auth.form.LoginUserVO;
import com.ruoyi.auth.form.UserLoginDTO;
import com.ruoyi.auth.form.WxMinSimpleLoginDTO;
import com.ruoyi.auth.service.SysLoginService;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 认证接口
 */
@RestController
@RequestMapping("/wxApp")
@Slf4j
public class AuthAppController {


    @Autowired
    private SysLoginService authService;

    /**
     * 小程序登录
     */
    @PostMapping("/wxMin")
    public R<LoginUserVO> wxMinLogin(@RequestBody  UserLoginDTO dto) {
        return R.ok(authService.wxMinLogin(dto));
    }

    /**
     * 小程序登录_内测版
     */
    @PostMapping("/wxMin/simple")
    public R<LoginUserVO> wxMinSimpleLogin(@RequestBody @Valid WxMinSimpleLoginDTO loginParams) {
        return R.ok(authService.wxMinSimpleLogin(loginParams.getPhoneNumber()));
    }

    /**
     * 传统登录-用户名+密码
     */
    @PostMapping
    public R<LoginUserVO> login(@RequestBody @Valid LoginBody form ) {
        return R.ok(authService.Applogin(form.getUsername(), form.getPassword()));
    }
}