package com.ruoyi.shop.domain.cart.dto;

import lombok.*;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/21
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {
    private Long skuId;
    private Boolean selected;
    private Integer count;
}
