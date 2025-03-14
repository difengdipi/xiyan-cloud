package com.ruoyi.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.category.domain.Goods;
import com.ruoyi.category.mapper.GoodsMapper;
import com.ruoyi.category.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
