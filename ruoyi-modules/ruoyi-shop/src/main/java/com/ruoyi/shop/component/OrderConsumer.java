package com.ruoyi.shop.component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.ruoyi.shop.controller.order.OrderController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @Description: 订单消费者
 * @author: zh
 * @Create : 2025/4/23
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class OrderConsumer {

    @Autowired
    private OrderController orderController;

    /**
     * 订单超时监听
     *
     * @param massage
     * @param channel
     * @param tag
     */
    @RabbitListener(
        bindings =
                {
                        @QueueBinding(value = @Queue(value = RabbitConfig.ORDER_DLX_QUEUE_NAME, durable = "true"),
                                exchange = @Exchange(value = RabbitConfig.ORDER_DLX_EXCHANGE_EXCHANGE), key = RabbitConfig.ORDER_DEAD_KEY)
                })
    @RabbitHandler
    public void processOrder(Message massage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        Long id = JSON.parseObject(new String(massage.getBody()), Long.class);
        if (null == id) {
            return;
        }
        try {
//            手动确认机制
            orderController.UpdateOrderStatus(id);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
