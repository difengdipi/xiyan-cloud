package com.ruoyi.shop.domain.order.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderSkuVo {
    /**
     * SKU ID
     */
    private Long id;

    /**
     * 商品 ID
     */
    private Integer spuId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品属性文字
     */
    private String attrsText;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 购买时单价（单位：分）
     */
    private BigDecimal curPrice;

    /**
     * 图片地址
     */
    private String image;

}
