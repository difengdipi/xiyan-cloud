package com.ruoyi.shop.service.impl.goods;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.mapper.goods.GoodsMapper;
import com.ruoyi.shop.service.goods.GoodsService;
import org.springframework.stereotype.Service;

@Service

public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
