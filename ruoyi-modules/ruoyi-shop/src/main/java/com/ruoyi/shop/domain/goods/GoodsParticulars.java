package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsParticulars {
    @TableId(type = IdType.AUTO)
    private Integer particularsId;
    private Integer goodsId;
    private Double oldPrice;
    private Double goodsDiscount;
    private Integer goodsInventory;
    private Integer brandId;
    private Double commentCount;
    private Integer salesCount;
    private Double collectCount;
    private String mainVideos;
    private List< String > mainPictures;
    private Integer categoryId;
    private Integer detailsId;
    private Integer specId;
    private String skusId;
    private Double videoScale;
    private Integer isPresale;
    private Integer isCollect;
    private String recommends;
    private String userAddresses;
    private Integer evluationInfoId;
}
