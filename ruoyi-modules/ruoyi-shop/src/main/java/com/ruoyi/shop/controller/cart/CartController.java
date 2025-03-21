package com.ruoyi.shop.controller.cart;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.shop.domain.cart.CartItem;
import com.ruoyi.shop.domain.cart.dto.CartDto;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.goods.GoodsParticulars;
import com.ruoyi.shop.domain.goods.GoodsSkus;
import com.ruoyi.shop.service.cart.ICartService;
import com.ruoyi.shop.service.goods.GoodsParticularsService;
import com.ruoyi.shop.service.goods.GoodsService;
import com.ruoyi.shop.service.goods.GoodsSkusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 购物车控制类
 * @author: zh
 * @Create : 2025/3/21
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/member/cart")
@Tag(name = "购物车控制类")
@Slf4j
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private GoodsSkusService goodsSkuService;

    @Autowired
    GoodsParticularsService goodSparticularsService;
    @Autowired
    GoodsService goodsService;

    @PostMapping("")
    @Operation(summary = "添加购物车")
    @Transactional
    public R addMemberCart(@RequestBody CartDto dto) {
        log.info("添加购物车:{}", dto);
        //根据skuid去查询对应的商品表
        GoodsSkus sku = goodsSkuService.getById(dto.getSkuId());
        if (ObjectUtils.isEmpty(sku)) {
            return R.fail("商品已下架");
        }
        //是否有效需要判断下单量与库存量
        GoodsParticulars one = goodSparticularsService.getOne(new LambdaQueryWrapper<GoodsParticulars>().eq(GoodsParticulars::getSkusId, dto.getSkuId()));
        if (one.getGoodsInventory() < dto.getCount()) {
            return R.fail("库存不足");
        }
        //根据商品编号去查名称
        Goods goods = goodsService.getById(one.getGoodsId());
        CartItem build = CartItem.builder()
                .skuId(dto.getSkuId())
                .name(goods.getGoodsName())
                .picture(one.getMainPictures())
                .price(new BigDecimal(sku.getSkuPrice()))
                .nowPrice(new BigDecimal(sku.getSkuOldPrice()))
                .stock(sku.getSkuInventory())
                .selected(false) //默认不选中
                .attrsText("")
                .isEffective(true).build();
        return cartService.save(build) ? R.ok() : R.fail("添加购物车失败");
    }
    @DeleteMapping("")
    @Operation(summary = "删除购物车商品")
    public R deleteCart(@RequestBody Long[] ids){
        log.info("删除购物车商品:{}",ids);
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<CartItem> eq = new LambdaQueryWrapper<CartItem>().eq(CartItem::getUserId, userId).eq(CartItem::getId, ids);
        return cartService.remove(eq)? R.ok() : R.fail("删除失败");
    }

    @PutMapping("/selected")
    @Operation(summary = "修改-购物车全选/取消")
    public R selected(@RequestBody Boolean selected,@RequestBody Long[] ids){
        log.info("修改-购物车:{}全选/取消:{}",selected,ids);
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<CartItem> set = new LambdaUpdateWrapper<CartItem>()
                .eq(CartItem::getUserId, userId)
                .in(CartItem::getId, ids)
                .set(CartItem::getSelected, selected);
        return cartService.update(set)? R.ok() : R.fail("修改失败");
    }

    @GetMapping("")
    @Operation(summary = "获取-购物车列表")
    public R  getCartItemList(){
        Long userId = SecurityUtils.getUserId();
        List<CartItem> list = cartService.list(new LambdaQueryWrapper<CartItem>().eq(CartItem::getUserId, userId));
        return R.ok(list);
    }

    @PostMapping("/merge")
    @Operation(summary = "合并购物车")
    //TODO 合并购物车
    public R mergeCart(@RequestBody List<CartDto> dto){
       return R.ok("功能待上线");
    }

    @PostMapping("/{id}")
    @Operation(summary = "修改购物车商品")
    public R mergeCart(@PathVariable("id") Long id,@RequestBody CartDto dto){
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<CartItem> set = new LambdaUpdateWrapper<CartItem>().eq(CartItem::getUserId, userId).eq(CartItem::getSkuId, id)
                .set(CartItem::getCount, dto.getCount())
                .set(CartItem::getSelected, dto.getSelected());
        cartService.update(set);
        return cartService.update(set)? R.ok() : R.fail("修改失败");

    }

}
