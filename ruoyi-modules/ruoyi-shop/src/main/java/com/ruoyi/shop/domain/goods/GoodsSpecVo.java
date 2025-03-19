package com.ruoyi.shop.domain.goods;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSpecVo {
    private Integer specId;
    private String specName;
    private List<GoodsValue> goodsValue;
}
