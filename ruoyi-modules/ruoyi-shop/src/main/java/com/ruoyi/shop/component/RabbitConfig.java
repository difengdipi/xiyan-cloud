package com.ruoyi.shop.component;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description: rabbitmq配置类
 * @author: zh
 * @Create : 2025/4/23
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
public class RabbitConfig {

    private static final long DELAY_TIME = 1000 * 60 * 5; // 单位：毫秒
    // 延迟队列
    public static final String ORDER_QUEUE_NAME = "ORDER_QUEUE";
    public static final String ORDER_EXCHANGE_NAME = "ORDER_EXCHANGE";
    public static final String ORDER_ROUTING_KEY = "ORDER_ROUTING_KEY";

    // 死信队列
    public static final String ORDER_DLX_QUEUE_NAME = "ORDER_DLX_QUEUE";
    public static final String ORDER_DLX_EXCHANGE_EXCHANGE = "ORDER_DLX_EXCHANGE_EXCHANGE";
    public static final String ORDER_DEAD_KEY = "ORDER_DEAD_KEY";

    // 1. 声明延迟队列（绑定死信交换机）
    @Bean(value="orderOverQueue")
    public Queue orderOverqueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE_EXCHANGE); // 死信交换机
        args.put("x-dead-letter-routing-key", ORDER_DEAD_KEY);         // 死信路由键
        args.put("x-message-ttl", DELAY_TIME);                          // TTL（毫秒）
        return new Queue(ORDER_QUEUE_NAME, true, false, false, args);
    }

    // 2. 声明死信队列
    @Bean(value = "orderDlxQueue")
    public Queue orderDlxQueue() {
        return new Queue(ORDER_DLX_QUEUE_NAME, true,false,false);
    }

    // 3. 声明延迟队列的交换机
    @Bean(value = "orderOverExchange")
    public DirectExchange orderOverExchange() {
        return new DirectExchange(ORDER_EXCHANGE_NAME, true, false);
    }

    // 4. 声明死信队列的交换机
    @Bean(value = "orderDeadExchange")
    public DirectExchange orderDeadExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE_EXCHANGE, true, false);
    }

    // 5. 绑定延迟队列到交换机
    @Bean
    public Binding bindingOrderOverDirect(@Qualifier("orderOverQueue")Queue orderOverQueue , @Qualifier("orderOverExchange")DirectExchange orderOverExchange) {
        return BindingBuilder.bind(orderOverQueue)
                .to(orderOverExchange)
                .with(ORDER_ROUTING_KEY);
    }

    // 6. 绑定死信队列到死信交换机
    @Bean
    public Binding bindingOrderDeadDirect(@Qualifier("orderDlxQueue")Queue orderDlxQueue , @Qualifier("orderDeadExchange")DirectExchange orderDeadExchange) {
        return BindingBuilder.bind(orderDlxQueue)
                .to(orderDeadExchange)
                .with(ORDER_DEAD_KEY);
    }
}