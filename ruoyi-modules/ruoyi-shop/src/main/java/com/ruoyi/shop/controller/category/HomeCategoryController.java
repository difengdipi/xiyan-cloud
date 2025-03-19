package com.ruoyi.shop.controller.category;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.shop.domain.category.Category;
import com.ruoyi.shop.domain.category.vo.CategoryItemVo;
import com.ruoyi.shop.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 小程序home分类controller
 * @author: zh
 * @Create : 2025/3/19
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/home/category")
@Tag(name = "小程序homeController")
public class HomeCategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/mutli")
    @Operation(summary = "前台分类")
    public R mutiliCategory(){
        List<Category> list = categoryService.list();
        ArrayList<CategoryItemVo> categoryItemVos = new ArrayList<>();
        for (Category category : list) {
            CategoryItemVo build = CategoryItemVo.builder().iconUrl(category.getCategoryIcon())
                    .id(category.getCategoryId())
                    .name(category.getCategoryName()).build();
            categoryItemVos.add(build);
        }
        return R.ok(categoryItemVos);
    }
}
