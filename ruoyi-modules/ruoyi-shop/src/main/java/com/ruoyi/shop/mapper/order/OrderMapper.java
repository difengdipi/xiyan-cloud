package com.ruoyi.shop.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.shop.domain.order.Order;
import com.ruoyi.shop.domain.order.vo.OrderResultVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderResultVo> selectOrderState(@Param("userId") Long userId,@Param("orderState") Integer orderState);
}
