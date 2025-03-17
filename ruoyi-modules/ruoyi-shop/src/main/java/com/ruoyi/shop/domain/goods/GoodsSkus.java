package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSkus {
    @TableId(type = IdType.AUTO)
    private Integer skuId;
    private Integer skuInventory;
    private Double skuOldPrice;
    private String skuPrice;
    private Integer skuCode;
    private String skuSpecsId;
}
