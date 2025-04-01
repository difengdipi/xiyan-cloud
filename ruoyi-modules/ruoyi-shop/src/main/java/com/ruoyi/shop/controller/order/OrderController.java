package com.ruoyi.shop.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.shop.domain.address.Address;
import com.ruoyi.shop.domain.cart.CartItem;
import com.ruoyi.shop.domain.order.dto.OrderPreGoods;
import com.ruoyi.shop.domain.order.dto.Summary;
import com.ruoyi.shop.domain.order.vo.PreOrderVo;
import com.ruoyi.shop.service.address.IAddressService;
import com.ruoyi.shop.service.cart.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/26
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/member/order")
@Tag(name = "会员订单")
public class OrderController {

    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService carService;
    @GetMapping("pre")
    @Operation(summary = "生成-订单(结算页)")
    public R preOrder() {
        //1、获取用户的地址列表
        List<Address> addressList = addressService.list(new LambdaQueryWrapper<Address>().eq(Address::getUserId, DentalUtils.getUserId()));
        //2、获取用户的选中的购物车列表
        List<CartItem> CartItemlist = carService.list(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, DentalUtils.getUserId())
                .eq(CartItem::getSelected, true)
        );
        ArrayList<OrderPreGoods> OrderPreGoodsList = new ArrayList<>();
        CartItemlist.stream().forEach(cartItem -> {
            OrderPreGoods orderPreGoods = new OrderPreGoods();
            BeanUtils.copyProperties(cartItem, orderPreGoods);
            orderPreGoods.setPayPrice(cartItem.getNowPrice());
            orderPreGoods.setId(cartItem.getGoodsId());
            orderPreGoods.setTotalPayPrice(cartItem.getNowPrice().multiply(BigDecimal.valueOf(cartItem.getCount())));
            OrderPreGoodsList.add(orderPreGoods);
        });
        //3、获取用户的优惠券列表
        Summary summary = new Summary();
        OrderPreGoodsList.stream().filter(Objects::nonNull).forEach(s->{
            summary.setTotalPrice(summary.getTotalPrice().add(s.getTotalPayPrice()));
        });
        if(summary.getTotalPrice().compareTo(BigDecimal.valueOf(20))>0){
            summary.setPostFee(BigDecimal.valueOf(0));
        }else{
            summary.setPostFee(BigDecimal.valueOf(10));
        }
        summary.setTotalPayPrice(summary.getTotalPrice().add(summary.getPostFee()));

        return R.ok(PreOrderVo.builder()
                .userAddresses(addressList)
                .goods(OrderPreGoodsList)
                .summary(summary)
                .build());
    }

}
