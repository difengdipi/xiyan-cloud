package com.ruoyi.shop.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation for generating all the getters/setters, toString, etc.
@NoArgsConstructor // Generates a constructor with no parameters
@AllArgsConstructor // Generates a constructor with all parameters
@Builder // Builder pattern for creating instances of this class
public class CartItemDTO {
    private String id; // 商品 ID
    private String skuId; // SKU ID
    private String name; // 商品名称
    private String attrsText; // 属性文字
    private String[] specs; // 规格
    private String picture; // 图片
    private Double price; // 加入时价格
    private Double nowPrice; // 当前的价格
    private Double nowOriginalPrice; // 当前原价
    private Boolean selected; // 是否选中
    private Integer stock; // 库存
    private Integer count; // 数量
    private Boolean isEffective; // 是否为有效商品
    private Object discount; // 折扣信息
    private Boolean isCollect; // 是否收藏
    private Integer postFee; // 邮费
}