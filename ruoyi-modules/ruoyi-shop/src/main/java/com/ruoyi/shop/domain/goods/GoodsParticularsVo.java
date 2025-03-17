package com.ruoyi.shop.domain.goods;

import lombok.Data;

@Data
public class GoodsParticularsVo {
    private Integer goodsId;
    private String goodsName;
    private String goodsDesc;
    private Double goodsPrice;
    private GoodsBrand goodsBrand;
    private GoodsSpecVo goodsSpec;
    private GoodsSkus goodsSkus;
    private PccVos category;
    private GoodsDetailsVo goodsDetails;
    private Goods similarProducts;
    private Goods hotByDay;
}
