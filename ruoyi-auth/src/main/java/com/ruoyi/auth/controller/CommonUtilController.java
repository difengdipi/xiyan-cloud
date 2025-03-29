package com.ruoyi.auth.controller;

import com.ruoyi.common.security.Util.DentalUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RequestMapping("/Common")
@RestController
public class CommonUtilController {
    @GetMapping("/getUserId")
    public Long getUserId(){
        return DentalUtils.getUserId();
    }
}
