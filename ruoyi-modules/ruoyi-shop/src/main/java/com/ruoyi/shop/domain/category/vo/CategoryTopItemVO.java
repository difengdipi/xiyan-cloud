package com.ruoyi.shop.domain.category.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTopItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CategoryChildItemVO> children; // 二级分类集合
    private String id; // 一级分类id
    private List<String> imageBanners; // 一级分类图片集
    private String name; // 一级分类名称
    private String picture; // 一级分类图片
}