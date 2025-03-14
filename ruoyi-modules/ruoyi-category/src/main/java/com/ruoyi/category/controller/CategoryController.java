package com.ruoyi.category.controller;

import com.ruoyi.category.domain.Category;
import com.ruoyi.category.service.CategoryService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/getAllCategory")
    public R getAllCategory() {
        List<Category> category = categoryService.list();
        return R.ok(category);
    }

    /**
     * 增加分类成功
     *
     * @param category
     * @return
     */
    @PostMapping(value = "/addCategory")
    @RequiresPermissions("category:category:add")
    public R addCategory(@RequestBody Category category) {
        boolean flag = categoryService.save(category);
        if (flag) {
            return R.ok("增加分类成功");
        } else {
            return R.fail("增加分类失败");
        }
    }

    @DeleteMapping(value = "/deleteCategory/{id}")
    @RequiresPermissions("category:category:remove")
    public R deleteCategory(@PathVariable("id") Integer categoryId) {
        boolean flag = categoryService.removeById(categoryId);
        if (flag) {
            return R.ok("删除分类成功");
        } else {
            return R.fail("删除分类失败");
        }
    }

    @GetMapping(value = "/getCategoryById/{categoryId}")
    public R getByIdCategory(@PathVariable("categoryId") Integer categoryId) {
        Category category = categoryService.getById(categoryId);
        return R.ok( category);
    }

    @PutMapping(value = "/updateCategory")
    @RequiresPermissions("category:category:edit")
    public R updateCategory(@RequestBody Category category) {
        boolean flag = categoryService.updateById(category);
        if (flag) {
            return R.ok("修改分类成功");
        } else {
            return R.fail("修改分类失败");
        }
    }
}
