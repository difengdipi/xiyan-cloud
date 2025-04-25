package com.ruoyi.dental.component;

import com.alibaba.fastjson.JSON;
import com.ruoyi.dental.domain.dto.DoctorNumsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class RabbitProduce {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送预约信息
     *
     * @param dto
     */
    public void sendOver(List<DoctorNumsDto> dto) {
        String mqMessage = JSON.toJSONString(dto);
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.USER_APP_EXCHANGE, RabbitConfig.USER_APP_ROUTING, mqMessage);
        }catch (Exception e){
            throw new RuntimeException("发送预约消息失败");
        }
    }
}
