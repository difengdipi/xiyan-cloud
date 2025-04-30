package com.ruoyi.sms.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.sms.handler.SmsTypeFactory;
import com.ruoyi.sms.inter.SmsComInterFace;
import com.ruoyi.system.api.constants.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户短信发送接口
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserSmsController {

    @Autowired
    SmsTypeFactory smsTypeFactory;

    /**
     * 给用户手机号发送就诊提示
     * @param phone
     * @param request
     * @return
     */
    @PostMapping("/sendUserApp/{phone}")
    @InnerAuth
    public R sendUserApp(@PathVariable("phone") String phone, @RequestBody SmsRequest request){
        SmsComInterFace sms =  smsTypeFactory.getSms(request.getType());
        return sms.sendSms(phone, request.getParam());
    }
    @GetMapping("/sendAllUserApp")
    public R<Boolean> sendAllUserApp(@RequestParam String phone){
        return null;
    }
}
