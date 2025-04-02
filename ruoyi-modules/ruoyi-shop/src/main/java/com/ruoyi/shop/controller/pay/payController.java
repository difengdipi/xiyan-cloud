package com.ruoyi.shop.controller.pay;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.service.order.OrderInfoIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "模拟微信小程序支付")
    @GetMapping("/mock")
    public R mockwxPayMini(@RequestParam Long orderId){
        //更改订单的状态
        orderInfoIService.update(new LambdaUpdateWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId,orderId)
                .eq(OrderInfo::getOrderState,1)
                .set(OrderInfo::getOrderState,2)
        );
        return R.ok();
    }
}
