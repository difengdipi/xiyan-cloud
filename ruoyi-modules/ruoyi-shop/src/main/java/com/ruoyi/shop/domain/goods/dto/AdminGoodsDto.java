package com.ruoyi.shop.domain.goods.dto;

import com.ruoyi.shop.domain.goods.GoodsBrand;
import com.ruoyi.shop.domain.goods.GoodsParticulars;
import com.ruoyi.shop.domain.goods.GoodsSkus;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/27
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public class AdminGoodsDto {
    //商品详情
    private GoodsParticulars goodsParticulars;
    //商品品牌
    private GoodsBrand goodsBrand;
    private List<GoodsSkus> goodsSkus;


}
