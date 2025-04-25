package com.ruoyi.shop.controller.category;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.shop.domain.category.Category;
import com.ruoyi.shop.domain.category.vo.CategoryChildItemVO;
import com.ruoyi.shop.domain.category.vo.CategoryTopItemVO;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.goods.vo.GoodsItemVO;
import com.ruoyi.shop.service.category.CategoryService;
import com.ruoyi.shop.service.goods.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.ruoyi.shop.constants.ShopCacheConstants.CATEGORY_KEY;

@RestController
@RequestMapping(value = "/category")
@Tag(name = "显示分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

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
    @Autowired
    RedisService redisService;
    @Operation(summary = "删除分类数据")
    @DeleteMapping(value = "/deleteCategory/{id}")
    public R deleteCategory(@PathVariable("id") Integer id) {
        boolean flag = categoryService.remove(new LambdaQueryWrapper<Category>().eq(Category::getCategoryId, id));
        CompletableFuture.runAsync(() -> {
            redisService.deleteObject(CATEGORY_KEY);
        });
        if (flag) {
            return R.ok("删除分类成功");
        } else {
            return R.fail("删除分类失败");
        }
    }

    @Operation(summary = "按照分类编号查询数据")
    @GetMapping(value = "/getCategoryById/{id}")
    public R getByIdCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryId, id)
        );
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

    @GetMapping("/top")
    @Cacheable(value = "category", key = "#root.methodName")
    public R<List<CategoryTopItemVO>> getCategoryTop() {
        // 查询顶级分类
        LambdaQueryWrapper<Category> query = new LambdaQueryWrapper<>();
        query.isNull(Category::getCategoryParent);
        List<Category> parentCategories = categoryService.list(query);

        // 转换顶级分类为视图对象
        List<CategoryTopItemVO> topItems = parentCategories.stream()
                .map(this::convertToCategoryTopItemVO)
                .collect(Collectors.toList());

        return R.ok(topItems);
    }

    private CategoryTopItemVO convertToCategoryTopItemVO(Category category) {
        CategoryTopItemVO vo = new CategoryTopItemVO();
        vo.setId(String.valueOf(category.getCategoryId()));
        vo.setName(category.getCategoryName());
        vo.setPicture(category.getCategoryIcon());
        vo.setImageBanners(new ArrayList<>()); // 根据实际情况填充

        // 查询该顶级分类下的所有子分类
        LambdaQueryWrapper<Category> childQuery = new LambdaQueryWrapper<>();
        childQuery.eq(Category::getCategoryParent, category.getCategoryId());
        List<Category> children = categoryService.list(childQuery);

        // 转换子分类为视图对象
        List<CategoryChildItemVO> childItems = children.stream()
                .map(this::convertToCategoryChildItemVO)
                .collect(Collectors.toList());
        if(childItems.isEmpty() || childItems.size() == 0){
            //根据category查询商品
            CategoryChildItemVO categoryChildItemVO = new CategoryChildItemVO();
            //根据付分类的id去查找商品信息
            LambdaQueryWrapper<Goods> goodsQuery = new LambdaQueryWrapper<>();
            goodsQuery.eq(Goods::getCategoryId, category.getCategoryId());
            List<Goods> goodsList = goodsService.list(goodsQuery);
            List<GoodsItemVO> goodsItemVOlist = new ArrayList<>();
            for(Goods goods : goodsList){
                GoodsItemVO build = GoodsItemVO.builder()
                        .id(String.valueOf(goods.getGoodsId()))
                        .name(goods.getGoodsName())
                        .desc(goods.getGoodsDesc())
                        .price(goods.getGoodsPrice())
                        .picture(goods.getGoodsPicture())
                        .orderNum(goods.getGoodsOrdernum())
                        .discount(goods.getGoodsPrice())
                        .build();
                goodsItemVOlist.add(build);
            }
            categoryChildItemVO.setGoods(goodsItemVOlist);
            childItems.add(categoryChildItemVO);
        }
        vo.setChildren(childItems);
        return vo;
    }

    private CategoryChildItemVO convertToCategoryChildItemVO(Category category) {
        CategoryChildItemVO vo = new CategoryChildItemVO();
        vo.setId(String.valueOf(category.getCategoryId()));
        vo.setName(category.getCategoryName());
        vo.setPicture(category.getCategoryIcon());

        // 查询该子分类下的所有商品
        LambdaQueryWrapper<Goods> goodsQuery = new LambdaQueryWrapper<>();
        goodsQuery.eq(Goods::getCategoryId, category.getCategoryId());
        List<Goods> goodsList = goodsService.list(goodsQuery);

        // 转换商品为视图对象
        List<GoodsItemVO> goodsItems = goodsList.stream()
                .map(this::convertToGoodsItemVO)
                .collect(Collectors.toList());

        vo.setGoods(goodsItems);
        return vo;
    }

    private GoodsItemVO convertToGoodsItemVO(Goods goods) {
        GoodsItemVO vo = new GoodsItemVO();
        BeanUtils.copyProperties(goods, vo);
        vo.setId(String.valueOf(goods.getGoodsId()));
        return vo;
    }

}
