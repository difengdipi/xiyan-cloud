package com.ruoyi.shop.domain.prod;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prodclass {
    @TableId(type = IdType.AUTO)
    private Integer prodclassId;
    private Integer categoryId;
    private Integer prodclassChildren;
    private String prodclassGoods;
}
