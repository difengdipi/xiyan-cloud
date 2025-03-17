package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBrand {
    @TableId(type = IdType.AUTO)
    private Integer brandId;
    private String brandLogo;
    private String brandName;
    private String brandNameEn;
    private String brandPicture;
    private String brandPlace;
    private String brandType;
}
