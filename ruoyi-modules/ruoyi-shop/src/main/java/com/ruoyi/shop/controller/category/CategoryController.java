package com.ruoyi.shop.controller.category;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.shop.domain.category.Category;
import com.ruoyi.shop.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category/")
@Tag(name = "显示分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获得所有的分类")
    @GetMapping(value = "/getAllCategory")
    public R getAllCategory() {
        List<Category> category = categoryService.list();
        return R.ok( category);
    }

    @Operation(summary = "增加分类")
    @PostMapping(value = "/addCategory")
    public R addCategory(@RequestBody Category category) {
        boolean flag = categoryService.save(category);
        if (flag) {
            return R.ok("增加分类成功");
        } else {
            return R.fail("增加分类失败");
        }
    }

    @Operation(summary = "删除分类数据")
    @DeleteMapping(value = "/deleteCategory/{id}")
    public R deleteCategory(@PathVariable("id") Integer bannerId) {
        boolean flag = categoryService.removeById(bannerId);
        if (flag) {
            return R.ok("删除分类成功");
        } else {
            return R.fail("删除分类失败");
        }
    }

    @Operation(summary = "按照分类编号查询数据")
    @GetMapping(value = "/getCategoryById/{id}")
    public R getByIdCategory(@PathVariable("id") Integer bannerId) {
        Category category = categoryService.getById(bannerId);
        return R.ok(category);
    }

    @Operation(summary = "修改分类数据")
    @PutMapping(value = "/updateCategory")
    public R updateCategory(@RequestBody Category category) {
        boolean flag = categoryService.updateById(category);
        if (flag) {
            return R.ok("修改分类成功");
        } else {
            return R.fail("修改分类失败");
        }
    }
}
