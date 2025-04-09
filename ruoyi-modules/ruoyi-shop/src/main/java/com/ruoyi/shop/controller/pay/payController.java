package com.ruoyi.shop.controller.pay;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.shop.domain.goods.GoodsSkus;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.domain.order.OrderSku;
import com.ruoyi.shop.service.goods.GoodsSkusService;
import com.ruoyi.shop.service.order.OrderInfoIService;
import com.ruoyi.shop.service.order.OrderSkuIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Description: 支付控制类
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/pay")
@Tag(name = "支付控制类")
@Slf4j
public class payController {

    /**
     * 微信小程序支付  --
     * @return
     */
    @GetMapping("/wxPay/miniPay")
    @Operation(summary = "微信小程序支付")
    public R  wxPayMini(@RequestParam Long orderId){
//        TODO: 微信小程序支付未实现
        return R.ok();
    }

    @Autowired
    OrderInfoIService orderInfoIService;
    @Autowired
    GoodsSkusService goodsSkusService;
    @Autowired
    OrderSkuIService orderSkuService;

    @Operation(summary = "模拟微信小程序支付")
    @GetMapping("/mock")
    public R mockwxPayMini(@RequestParam Long orderId){
        //更改订单的状态
        orderInfoIService.update(new LambdaUpdateWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId,orderId)
                .eq(OrderInfo::getOrderState,1)
                .set(OrderInfo::getOrderState,2)
        );
        CompletableFuture.runAsync(()->{
            // 获取订单中的所有sku
            List<OrderSku> list = orderSkuService.list(
                    new LambdaUpdateWrapper<OrderSku>()
                            .eq(OrderSku::getOrderId, orderId)
            );
            // 提取所有skuId
            Set<Long> skuIds = list.stream()
                    .map(OrderSku::getSkuId)
                    .collect(Collectors.toSet());
            // 创建skuId到quantity的映射
            Map<Long, Integer> skuQuantityMap = list.stream()
                    .collect(Collectors.toMap(
                            OrderSku::getSkuId,
                            OrderSku::getQuantity,
                            (oldVal, newVal) -> oldVal // 如果有重复skuId，保留第一个
                    ));
            // 批量更新库存（每个sku减去对应的quantity）
            for (Long skuId : skuIds) {
                Integer quantity = skuQuantityMap.get(skuId);
                if (quantity != null && quantity > 0) {
                    goodsSkusService.update(
                            new LambdaUpdateWrapper<GoodsSkus>()
                                    .eq(GoodsSkus::getSkuId, skuId)
                                    .setSql("sku_inventory = sku_inventory - " + quantity)
                                    // 添加库存不足检查
                                    .gt(GoodsSkus::getSkuInventory, quantity - 1)
                    );
                }
            }
        }).whenComplete((v,e)->{
            if(e!=null){
                log.info("异步任务执行失败");
            }
            if(v != null){
                log.info("模拟微信小程序支付---修改库存成功");
            }
        });
        return R.ok();
    }
}
