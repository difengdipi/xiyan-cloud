package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Summary {

    /**
     * 商品总价
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;

    /**
     * 邮费
     */
    private BigDecimal postFee;

    /**
     * 应付金额 = 商品总价 + 邮费 - 优惠券金额 - 积分抵扣金额 - 优惠券抵
     */
    private BigDecimal totalPayPrice = BigDecimal.ZERO;
}