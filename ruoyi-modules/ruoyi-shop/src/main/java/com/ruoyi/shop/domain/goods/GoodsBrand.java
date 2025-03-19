package com.ruoyi.shop.domain.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBrand {
    private Integer brandId;
    private String brandLogo;
    private String brandName;
    private String brandNameEn;
    private String brandPicture;
    private String brandPlace;
    private String brandType;
}
