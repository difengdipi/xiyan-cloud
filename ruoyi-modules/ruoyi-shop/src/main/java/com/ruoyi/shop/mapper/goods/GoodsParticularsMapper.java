package com.ruoyi.shop.mapper.goods;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.shop.domain.goods.GoodsParticulars;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoodsParticularsMapper extends BaseMapper<GoodsParticulars> {

    @Select("SELECT * FROM sys_goods_particulars WHERE JSON_CONTAINS(skus_id, #{skuId})")
    GoodsParticulars findBySkuId(Integer skuId);
}

