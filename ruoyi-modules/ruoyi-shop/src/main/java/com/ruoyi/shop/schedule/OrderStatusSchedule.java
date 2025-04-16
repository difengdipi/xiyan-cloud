package com.ruoyi.shop.schedule;

import com.ruoyi.common.redis.Constants.ShopConstants;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.service.order.OrderInfoIService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: 用于订单状态定时任务
 * @author: zh
 * @Create : 2025/4/16
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class OrderStatusSchedule {

    @Autowired
    OrderInfoIService orderInfoIService;
    @Autowired
    RedissonClient RedissonClient;
    /**
     * 判断订单是否超时--容错的--每60秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void orderTimeout() {
        RLock lock = RedissonClient.getLock(ShopConstants.CACHE_KEY_ORDERINFO_LOCK);
        try {
            if (lock.tryLock(1,30, TimeUnit.SECONDS)) {
                List<OrderInfo> list = orderInfoIService.list();
                List<OrderInfo> collect = list.stream()
                        .filter(orderInfo -> orderInfo.getOrderState() == 1)
                        .map(orderInfo -> {
                            LocalDateTime localDateTime = orderInfo.getCreateTime().plusSeconds(60);
                            LocalDateTime now = LocalDateTime.now();
                            if (localDateTime.isBefore(now)) {
                                orderInfo.setOrderState(6);
                                orderInfo.setCancelReason("订单超时");
                                orderInfo.setCountdown(-1);
                                orderInfo.setUpdateTime(LocalDateTime.now());
                            }
                            return orderInfo;
                        }).collect(Collectors.toList());
                //批量更新
                boolean b = orderInfoIService.updateBatchById(collect);
            }
        }catch (Exception e){
            log.info("判断订单超时错误");
        }
    }
}
