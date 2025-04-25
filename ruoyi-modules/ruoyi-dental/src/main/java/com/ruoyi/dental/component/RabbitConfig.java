package com.ruoyi.dental.component;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
public class RabbitConfig {
    //预约队列
    public static final String USER_APP_QUEUE = "USER_APP_QUEUE";
    public static final String USER_APP_EXCHANGE = "USER_APP_EXCHANGE";
    public static final String USER_APP_ROUTING= "USER_APP_ROUTING";

    /**
     * 声明队列
     * @return
     */
    @Bean("UserAppQueue")
    public Queue UserAppQueue() {
        return new Queue(USER_APP_QUEUE, true,false, false);
    }

    /**
     * 声明交换机
     * @return
     */
    @Bean("UserAppExchange")
    public DirectExchange UserAppExchange(){
        return new DirectExchange(USER_APP_EXCHANGE, true, false);
    }

    /**
     * 绑定队列到交换机
     *
     * @param UserAppQueue
     * @param UserAppExchange
     * @return
     */
    @Bean
    public Binding bindingUserAppDirect(@Qualifier("UserAppQueue")Queue UserAppQueue , @Qualifier("UserAppExchange")DirectExchange UserAppExchange) {
        return BindingBuilder.bind(UserAppQueue)
                .to(UserAppExchange)
                .with(USER_APP_ROUTING);
    }
}
