package com.ruoyi.shop.domain.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IncomeTrendVO {
    public enum TimeUnit {
        DAY, MONTH
    }
    
    private TimeUnit timeUnit;
    
    private List<IncomeTrendItemVO> items;
    
    private BigDecimal totalAmount;
    
    private Integer totalCount;
}
