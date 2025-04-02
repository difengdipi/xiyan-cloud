package com.ruoyi.shop.domain.order.vo;

import lombok.Data;

import java.util.List;

/**
 * 订单列表分页结果
 */
@Data
public class OrderListResult {
    /**
     * 总记录数
     */
    private Long counts;

    /**
     * 数据集合 [订单信息]
     */
    private List<OrderItem> items;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 页尺寸
     */
    private Integer pageSize;
}
