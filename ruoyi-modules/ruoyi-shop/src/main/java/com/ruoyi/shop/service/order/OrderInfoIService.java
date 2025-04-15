package com.ruoyi.shop.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.domain.order.dto.OrderAdminInfoDto;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public interface OrderInfoIService extends IService<OrderInfo> {

    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单管理列表
     *
     * @param orderInfo 订单管理
     * @return 订单管理集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的订单管理主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    /**
     * 删除订单管理信息
     *
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 管理前台的订单数据
     * @return
     */
    List<OrderAdminInfoDto>  listAll();
}
