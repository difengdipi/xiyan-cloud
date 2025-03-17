package com.ruoyi.shop.domain.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsDetailsVo {
    private Integer detailsId;
    private List< String > detailsPictures;
    private GoodsProperty goodsProperty;
}
