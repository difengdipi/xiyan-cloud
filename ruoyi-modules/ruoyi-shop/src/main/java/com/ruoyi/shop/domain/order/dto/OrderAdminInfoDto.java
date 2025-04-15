package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/15
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class OrderAdminInfoDto {
    //订单状态
    private Integer orderState;
    //总数
    private Long num;
    //金额
    private BigDecimal totalMoney;
}
