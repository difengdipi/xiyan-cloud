package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncomeTrendItemVO {
    private String date;
    
    private BigDecimal amount;
    
    private Integer count;
}