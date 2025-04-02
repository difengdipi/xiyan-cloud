package com.ruoyi.shop.domain.order.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartVo {
    private Long skuId;
    private Integer count;
}
