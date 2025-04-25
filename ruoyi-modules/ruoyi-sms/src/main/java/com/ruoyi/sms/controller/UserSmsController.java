package com.ruoyi.sms.controller;

import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 给用户手机号发送就诊提示
     * @param param
     * @param phone
     * @return
     */
    @GetMapping("/sendUserApp/{phone}")
    public R<Boolean> sendUserApp(@RequestParam Map<String, Object> param, @PathVariable("phone") String phone){
        return null;
    }
}
