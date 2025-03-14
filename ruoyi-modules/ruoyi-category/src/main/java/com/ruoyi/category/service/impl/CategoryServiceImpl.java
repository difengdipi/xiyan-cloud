package com.ruoyi.category.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.category.domain.Category;
import com.ruoyi.category.mapper.CategoryMapper;
import com.ruoyi.category.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
