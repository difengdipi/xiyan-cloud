package com.ruoyi.shop.component;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 订单生产者
 * @author: zh
 * @Create : 2025/4/23
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class OrderProduce {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送延时订单MQ
     *
     * @param id
     */
    public void sendOver(Long id) {
        String mqMessage = JSON.toJSONString(id);
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE_NAME, RabbitConfig.ORDER_ROUTING_KEY, mqMessage);
        }catch (Exception e){
            throw new RuntimeException("发送邀请消息失败");
        }
        }
}
