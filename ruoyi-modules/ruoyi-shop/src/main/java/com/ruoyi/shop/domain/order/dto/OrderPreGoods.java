package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class OrderPreGoods {

    /**
     * 属性文字，例如“颜色:瓷白色 尺寸：8寸”
     */
    private String attrsText; // 属性文字

    /**
     * 数量
     */
    private Integer count;

    /**
     * 商品 ID
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 实付单价
     */
    private BigDecimal payPrice;

    /**
     * 图片
     */
    private String picture;

    /**
     * 原单价
     */
    private BigDecimal price;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 实付价格小计
     */
    private BigDecimal totalPayPrice;
}
