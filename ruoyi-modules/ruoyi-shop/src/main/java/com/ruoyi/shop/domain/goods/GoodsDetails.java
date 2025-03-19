package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetails {
    @TableId(type = IdType.AUTO)
    private Integer detailsId;
    private String detailsPictures;
    private String detailsProperty;
}
