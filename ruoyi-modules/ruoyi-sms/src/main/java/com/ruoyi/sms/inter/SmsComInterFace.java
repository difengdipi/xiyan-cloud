package com.ruoyi.sms.inter;

import com.ruoyi.common.core.domain.R;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 统一短信接口设计模式
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public interface SmsComInterFace {
    public R sendSms(String phoneNumber, Map<String, Object> templateParams);
}
