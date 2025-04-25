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

import java.io.IOException;
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
    public void processOrder(Message message, Channel channel) {
        try{
//            手动确认机制
            List<DoctorNumsDto> dto = JSON.parseArray(new String(message.getBody()), DoctorNumsDto.class);
            if (dto == null) {
                // 消息处理失败，拒绝消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            // 手动确认机制
            doctorsController.updateAppList(dto);
            // 消息处理成功，确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息处理失败，拒绝消息
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
