package com.ruoyi.shop.controller.cart;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.shop.config.Utils;
import com.ruoyi.shop.domain.cart.CartItem;
import com.ruoyi.shop.domain.cart.dto.CartDto;
import com.ruoyi.shop.domain.cart.dto.DeCartDto;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.goods.GoodsParticulars;
import com.ruoyi.shop.domain.goods.GoodsSkus;
import com.ruoyi.shop.domain.goods.SkusSpec;
import com.ruoyi.shop.mapper.goods.GoodsParticularsMapper;
import com.ruoyi.shop.service.cart.ICartService;
import com.ruoyi.shop.service.goods.GoodsParticularsService;
import com.ruoyi.shop.service.goods.GoodsService;
import com.ruoyi.shop.service.goods.GoodsSkusService;
import com.ruoyi.shop.service.goods.SkusSpecService;
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
    @Autowired
    GoodsParticularsMapper goodsPartsMapper;

    @Autowired
    SkusSpecService skusSpecService;
    @PostMapping("")
    @Operation(summary = "添加购物车")
    @Transactional
    public R addMemberCart(@RequestBody CartDto dto) {
        //根据skuid去查询对应的商品表
        GoodsSkus sku = goodsSkuService.getById(dto.getSkuId());
        if (ObjectUtils.isEmpty(sku)) {
            return R.fail("商品已下架");
        }
        if (sku.getSkuInventory() < dto.getCount()) {
            return R.fail("库存不足");
        }
        //判断购物车中是否有该商品，如果有则更新数量
        CartItem cartItem = cartService.getOne(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, DentalUtils.getUserId())
                .eq(CartItem::getSkuId, dto.getSkuId())
        );
        if(!ObjectUtils.isEmpty(cartItem)){
            cartItem.setCount(cartItem.getCount() + dto.getCount());
            if(cartItem.getCount() > sku.getSkuInventory()){
                return R.fail("库存不足");
            }
            return cartService.updateById(cartItem) ? R.ok() : R.fail("添加购物车失败");
        }

        //根据skuId去查找对应的商品规格的信息，并拼接为对应的属性值
        StringBuilder attrsText = new StringBuilder();
        GoodsSkus goodsSkus = goodsSkuService.getById(sku.getSkuId());
        List<String> SkuSpecs = Utils.split(goodsSkus.getSkuSpecsId());
        skusSpecService.list(new LambdaQueryWrapper<SkusSpec>()
                .in(SkusSpec::getSpecId, SkuSpecs)
        ).forEach(skusSpec -> {
            attrsText.append(skusSpec.getSpecName()).append(":").append(skusSpec.getSpecValueName()).append(" ");
        });
        GoodsParticulars one = goodsPartsMapper.selectOne(new LambdaQueryWrapper<GoodsParticulars>()
                .like(GoodsParticulars::getSkusId, String.format(",%s,", sku.getSkuId()))
                // 或者处理首尾情况
                .or()
                .likeRight(GoodsParticulars::getSkusId, sku.getSkuId() + ",")
                .or()
                .likeLeft(GoodsParticulars::getSkusId, "," + sku.getSkuId())
                .or()
                .eq(GoodsParticulars::getSkusId, sku.getSkuId())
        );

        //根据商品编号去查名称
        Goods goods = goodsService.getById(one.getGoodsId());
        CartItem build = CartItem.builder()
                .skuId(dto.getSkuId())
                .goodsId(goods.getGoodsId())
                .name(goods.getGoodsName())
                .userId(DentalUtils.getUserId())
                .picture(one.getMainPictures())
                .price(new BigDecimal(sku.getSkuPrice()))
                .nowPrice(new BigDecimal(sku.getSkuOldPrice()))
                .stock(sku.getSkuInventory())
                .count(dto.getCount())
                .selected(false) //默认不选中
                .attrsText(attrsText.toString())
                .isEffective(true).build();
        log.info("CartItem:{}",build);
        return cartService.save(build) ? R.ok() : R.fail("添加购物车失败");
    }
    @DeleteMapping("")
    @Operation(summary = "删除购物车商品")
    public R deleteCart(@RequestBody DeCartDto dto){
        Long userId = DentalUtils.getUserId();
        LambdaQueryWrapper<CartItem> eq = new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, userId)
                .in(CartItem::getSkuId, dto.getIds());
        boolean remove = cartService.remove(eq);
        return remove? R.ok() : R.fail("删除失败");
    }

    @PutMapping("/selected")
    @Operation(summary = "修改-购物车全选/取消")
    public R selected(@RequestBody DeCartDto dto){
        Long userId = DentalUtils.getUserId();
        LambdaUpdateWrapper<CartItem> set = new LambdaUpdateWrapper<CartItem>()
                .eq(CartItem::getUserId, userId)
                .set(CartItem::getSelected, dto.getSelected());
        return cartService.update(set)? R.ok() : R.fail("修改失败");
    }

    @GetMapping("")
    @Operation(summary = "获取-购物车列表")
    public R  getCartItemList(){
        Long userId = DentalUtils.getUserId();
        List<CartItem> list = cartService.list(new LambdaQueryWrapper<CartItem>().eq(CartItem::getUserId, userId));
        return R.ok(list);
    }

    @PostMapping("/merge")
    @Operation(summary = "合并购物车")
    //TODO 合并购物车
    public R mergeCart(@RequestBody List<CartDto> dto){
       return R.ok("功能待上线");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改购物车商品")
    public R mergeCart(@PathVariable("id") Long id,@RequestBody CartDto dto){
        Long userId = DentalUtils.getUserId();
        LambdaUpdateWrapper<CartItem> set = new LambdaUpdateWrapper<CartItem>()
                .eq(CartItem::getUserId, userId)
                .eq(CartItem::getSkuId, id);
        if(dto.getCount() != null){
            set.set(CartItem::getCount,dto.getCount());
        }
        if(dto.getSelected() != null){
            set.set(CartItem::getSelected,dto.getSelected());
        }
        cartService.update(set);
        return cartService.update(set)? R.ok() : R.fail("修改失败");
    }

}
