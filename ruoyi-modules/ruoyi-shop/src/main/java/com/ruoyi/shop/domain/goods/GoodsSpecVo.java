package com.ruoyi.shop.domain.goods;

import lombok.Data;

@Data
public class GoodsSpecVo {
    private Integer specId;
    private String specName;
    private GoodsValue goodsValue;
}
