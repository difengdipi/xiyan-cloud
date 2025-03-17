package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class Goods {
    @TableId(type = IdType.AUTO)
    private Integer goodsId;
    private String goodsName;
    private String goodsDesc;
    private Double goodsPrice;
    private String goodsPicture;
    private Integer goodsOrdernum;
}
