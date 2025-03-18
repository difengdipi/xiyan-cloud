package com.ruoyi.shop.domain.goods.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String desc; // 商品描述
    private Double discount; // 商品折扣
    private String id; // 商品id
    private String name; // 商品名称
    private Integer orderNum; // 商品已下单数量
    private String picture; // 商品图片
    private Double price; // 商品价格
}