package com.ruoyi.category.controller;

import com.ruoyi.category.domain.Goods;
import com.ruoyi.category.service.GoodsService;
import com.ruoyi.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/goods/")
@Tag(name = "显示商品")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    

    @Operation(summary = "获得所有的商品")
    @GetMapping(value = "/getAllGoods")
    public R getAllGoods() {
        List<Goods> goods = goodsService.list();
        return R.ok( goods);
    }

    @Operation(summary = "增加商品")
    @PostMapping(value = "/addGoods")
    public R addGoods(@RequestBody Goods goods) {
        boolean flag = goodsService.save(goods);
        if (flag) {
            return R.ok("增加商品成功");
        } else {
            return R.fail("增加商品失败");
        }
    }

    @Operation(summary = "删除商品数据")
    @DeleteMapping(value = "/deleteGoods/{id}")
    public R deleteGoods(@PathVariable("id") Integer goodsId) {
        boolean flag = goodsService.removeById(goodsId);
        if (flag) {
            return R.ok("删除商品成功");
        } else {
            return R.fail("删除商品失败");
        }
    }

    @Operation(summary = "按照商品编号查询数据")
    @GetMapping(value = "/getGoodsById/{id}")
    public R getGoodsById(@PathVariable("id") Integer goodsId) {
        Goods goods = goodsService.getById(goodsId);
        return R.ok( goods);
    }


    @Operation(summary = "修改商品数据")
    @PutMapping(value = "/updateGoods")
    public R updateGoods(@RequestBody Goods goods) {
        boolean flag = goodsService.updateById(goods);
        if (flag) {
            return R.ok("修改商品成功");
        } else {
            return R.fail("修改商品失败");
        }
    }
}
