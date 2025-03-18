package com.ruoyi.shop.domain.category.vo;

import com.ruoyi.shop.domain.goods.vo.GoodsItemVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryChildItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<GoodsItemVO> goods; // 商品集合
    private String id; // 二级分类id
    private String name; // 二级分类名称
    private String picture; // 二级分类图片
}