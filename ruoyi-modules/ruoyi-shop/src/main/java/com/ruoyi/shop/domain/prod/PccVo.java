package com.ruoyi.shop.domain.prod;

import com.ruoyi.shop.domain.goods.Goods;
import lombok.Data;

import java.util.List;

@Data
public class PccVo {
    private Integer pccId;
    private String pccName;
    private String pccPicture;
    private Integer pccParent;
    private List<Goods> goodsList;
}
