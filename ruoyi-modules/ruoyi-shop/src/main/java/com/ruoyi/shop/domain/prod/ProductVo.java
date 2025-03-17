package com.ruoyi.shop.domain.prod;

import lombok.Data;

import java.util.List;

@Data
public class ProductVo {
    private Integer categoryId;
    private String categoryName;
    private String categoryIcon;
    private List< PccVo > prodChildren;
}
