package com.ruoyi.shop.service.impl.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.shop.domain.order.Order;
import com.ruoyi.shop.mapper.order.OrderMapper;
import com.ruoyi.shop.service.order.OrderIService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderIService {
}
