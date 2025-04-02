package com.ruoyi.shop.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_order_sku")
public class OrderSku {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 订单主键
     */
    private Long orderId;

    /**
     * SKU ID
     */
    private Long skuId;
    /**
     * 商品id
     */
    private Integer spuId;

    /**
     * 购买时单价
     */
    private BigDecimal curPrice;

    /**
     * 商品属性文字
     */
    private String attrsText;

    /**
     * 下单数量
     */
    private Integer quantity;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}