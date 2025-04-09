package com.ruoyi.shop.service.impl.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.mapper.order.OrderInfoMapper;
import com.ruoyi.shop.service.order.OrderInfoIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoIService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    @Override
    public OrderInfo selectOrderInfoById(Long id)
    {
        return orderInfoMapper.selectOrderInfoById(id);
    }

    /**
     * 查询订单管理列表
     *
     * @param orderInfo 订单管理
     * @return 订单管理
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo)
    {
        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    /**
     * 新增订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    @Override
    public int insertOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setCreateTime(LocalDateTime.now());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    /**
     * 修改订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    @Override
    public int updateOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setUpdateTime(LocalDateTime.now());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByIds(Long[] ids)
    {
        return orderInfoMapper.deleteOrderInfoByIds(ids);
    }

    /**
     * 删除订单管理信息
     *
     * @param id 订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoById(Long id)
    {
        return orderInfoMapper.deleteOrderInfoById(id);
    }

}
