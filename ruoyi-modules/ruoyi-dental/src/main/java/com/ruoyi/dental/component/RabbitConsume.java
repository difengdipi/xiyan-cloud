package com.ruoyi.dental.component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.ruoyi.dental.controller.DoctorsController;
import com.ruoyi.dental.domain.dto.DoctorNumsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class RabbitConsume {
    @Autowired
    public DoctorsController doctorsController;
    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.USER_APP_QUEUE, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.USER_APP_EXCHANGE), key = RabbitConfig.USER_APP_ROUTING)
                    })
    @RabbitHandler
    public void processOrder(Message massage, Channel channel) {
        DoctorNumsDto dto = JSON.parseObject(new String(massage.getBody()), DoctorNumsDto.class);
        if (null == dto) {
            return;
        }
        try {
//            手动确认机制
            doctorsController.updateAppNum(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
