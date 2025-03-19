package com.ruoyi.shop.domain.goods;

import com.ruoyi.shop.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsParticularsVo {
    private Integer id;

    private String goodsName;

    private String goodsDesc;

    private Double goodsPrice;

    // 如果 GoodsBrand 类不需要去前缀，则无需添加@JsonProperty注解
    private GoodsBrand goodsBrand;

    // 同样地，如果 GoodsSpecVo, GoodsSkus, PccVos, GoodsDetailsVo 不需要去掉前缀，则保留原样或根据需要调整
    private List<GoodsSpecVo> goodsSpec;
    private List<GoodsSkus> goodsSkus;

    private List<String> mainPictures;
    private PccVos category;
    private GoodsDetailsVo goodsDetails;

    // 假设 similarProducts 和 hotByDay 也需要去掉前缀
    private Goods similarProducts;

    private Goods hotByDay;

    private List<Address> addressList;
}