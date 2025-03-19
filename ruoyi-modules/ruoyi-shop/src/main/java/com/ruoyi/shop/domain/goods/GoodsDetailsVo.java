package com.ruoyi.shop.domain.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDetailsVo {
    private Integer detailsId;
    private List< String > detailsPictures;
    private List<GoodsProperty> goodsProperty;
}
