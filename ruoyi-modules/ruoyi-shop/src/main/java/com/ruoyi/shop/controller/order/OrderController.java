package com.ruoyi.shop.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.shop.config.Utils;
import com.ruoyi.shop.domain.address.Address;
import com.ruoyi.shop.domain.cart.CartItem;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.goods.GoodsParticulars;
import com.ruoyi.shop.domain.goods.GoodsSkus;
import com.ruoyi.shop.domain.goods.SkusSpec;
import com.ruoyi.shop.domain.order.Order;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.domain.order.OrderSku;
import com.ruoyi.shop.domain.order.dto.*;
import com.ruoyi.shop.domain.order.vo.*;
import com.ruoyi.shop.mapper.goods.GoodsParticularsMapper;
import com.ruoyi.shop.mapper.order.OrderMapper;
import com.ruoyi.shop.service.address.IAddressService;
import com.ruoyi.shop.service.cart.ICartService;
import com.ruoyi.shop.service.goods.GoodsParticularsService;
import com.ruoyi.shop.service.goods.GoodsService;
import com.ruoyi.shop.service.goods.GoodsSkusService;
import com.ruoyi.shop.service.goods.SkusSpecService;
import com.ruoyi.shop.service.order.OrderIService;
import com.ruoyi.shop.service.order.OrderInfoIService;
import com.ruoyi.shop.service.order.OrderSkuIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
@Slf4j
public class OrderController {

    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService carService;
    @Autowired
    OrderIService orderIService;
    @Autowired
    OrderSkuIService orderSkuService;
    @Autowired
    GoodsParticularsService goodsParticularsService;
    @Resource
    OrderInfoIService orderInfoService;
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

    @Autowired
    GoodsSkusService goodsSkuService;

    /**
     *
     * @param orderDto
     * @return id   订单id
     */
    @PostMapping("")
    @Operation(summary="提交-订单")
    @Transactional
    public R addOrder(@RequestBody OrderDto orderDto){
        //1.根据提交订单信息创建订单
        Order order = new Order();
        BeanUtils.copyProperties(orderDto,order);
        order.setUserId(DentalUtils.getUserId());
        orderIService.save(order);
        //2.根据订单id创建订单规格表
        List<OrderSku> orderSkuList = new ArrayList<>();
        List<CartVo> cartVoList = orderDto.getGoods();
        List<String> skuids = cartVoList.stream().map(CartVo::getSkuId).map(String::valueOf).collect(Collectors.toList());
        //3.查询购物车信息
        List<CartItem> list = carService.list(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, DentalUtils.getUserId())
                .in(CartItem::getSkuId, cartVoList.stream().map(CartVo::getSkuId).collect(Collectors.toList()))
        );
        BigDecimal reduce = BigDecimal.ZERO;
        BigDecimal postFee = BigDecimal.ZERO;
        //获取地址信息
        Address address = addressService.getById(orderDto.getAddressId());
        //4.购物车中没有数据
        if(list.isEmpty()){
            //根据添加购物车的模式，进行添加订单
            List<GoodsSkus> goodsSkulist = goodsSkuService.list(new LambdaQueryWrapper<GoodsSkus>().in(GoodsSkus::getSkuId, skuids));
            //进行判断货物是否下架
//            List<GoodsSkus> collect = goodsSkulist.stream().filter(Objects::isNull).collect(Collectors.toList());
//            if(!ObjectUtils.isEmpty(collect)){
//                return R.fail("包含已下架商品");
//            }
//            //进行判断货物是否有货
            Map<Long, Integer> cartVoListMap = cartVoList.stream().collect(Collectors.toMap(CartVo::getSkuId, CartVo::getCount));
//            List<GoodsSkus> collect1 = goodsSkulist.stream().filter(s -> s.getSkuInventory() < cartVoListMap.get(s.getSkuId())).collect(Collectors.toList());
//            if(!ObjectUtils.isEmpty(collect1)){
//                return R.fail("库存不足");
//            }
//            //规格map
//            HashMap<Integer, List<String>> goodsSkulistMap = new HashMap<>();
//            //属性文字map
//            HashMap<Integer, String> attrsTextMap = new HashMap<>();
//            goodsSkulist.stream().map(s->{
//                goodsSkulistMap.put(s.getSkuId(),Utils.split(s.getSkuSpecsId()));
//                return s;
//            });
//            //遍历规格map查找对应的属性文字
//            goodsSkulistMap.forEach((k,v)->{
//                StringBuilder attrsText = new StringBuilder();
//                skusSpecService.list(new LambdaQueryWrapper<SkusSpec>()
//                        .in(SkusSpec::getSpecId, v)
//                ).forEach(skusSpec -> {
//                    attrsText.append(skusSpec.getSpecName()).append(":").append(skusSpec.getSpecValueName()).append(" ");
//                });
//                attrsTextMap.put(k,attrsText.toString());
//            });
            //构造对应的orderSkuList
            for (GoodsSkus goodsSkus : goodsSkulist) {
                if (ObjectUtils.isEmpty(goodsSkus)) {
                    return R.fail("商品已下架");
                }
                Long skuId = goodsSkus.getSkuId().longValue();
                if (goodsSkus.getSkuInventory() < cartVoListMap.get(skuId)) {
                    return R.fail("库存不足");
                }
                //根据skuId去查找对应的商品规格的信息，并拼接为对应的属性值
                StringBuilder attrsText = new StringBuilder();
                List<String> SkuSpecs = Utils.split(goodsSkus.getSkuSpecsId());
                skusSpecService.list(new LambdaQueryWrapper<SkusSpec>()
                        .in(SkusSpec::getSpecId, SkuSpecs)
                ).forEach(skusSpec -> {
                    attrsText.append(skusSpec.getSpecName()).append(":").append(skusSpec.getSpecValueName()).append(" ");
                });
                GoodsParticulars one = goodsPartsMapper.selectOne(new LambdaQueryWrapper<GoodsParticulars>()
                        .like(GoodsParticulars::getSkusId, String.format(",%s,", goodsSkus.getSkuId()))
                        // 或者处理首尾情况
                        .or()
                        .likeRight(GoodsParticulars::getSkusId, goodsSkus.getSkuId() + ",")
                        .or()
                    .likeLeft(GoodsParticulars::getSkusId, "," + goodsSkus.getSkuId())
                    .or()
                    .eq(GoodsParticulars::getSkusId, goodsSkus.getSkuId())
                );
            //根据商品编号去查名称
            Goods goods = goodsService.getById(one.getGoodsId());
            OrderSku build = OrderSku.builder()
                    .orderId(order.getId())
                    .skuId(Long.parseLong(goodsSkus.getSkuId().toString()))
                    .name(goods.getGoodsName())
                    .spuId(goods.getGoodsId())
                    .attrsText(attrsText.toString())
                    .curPrice(new BigDecimal(goodsSkus.getSkuPrice()))
                    .image(one.getMainPictures().split(",")[1])
                    .quantity(cartVoListMap.get(skuId))
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            orderSkuList.add(build);
        }
        orderSkuService.saveBatch(orderSkuList);
        }else{
            //构造对应的orderSkuList
            list.stream().forEach(cartItem -> {
                orderSkuList.add(
                        OrderSku.builder()
                                .orderId(order.getId())
                                .skuId(cartItem.getSkuId())
                                .name(cartItem.getName())
                                .spuId(cartItem.getGoodsId())
                                .attrsText(cartItem.getAttrsText())
                                .curPrice(cartItem.getNowPrice())
                                .image(cartItem.getPicture().split(",")[0])
                                .quantity(cartItem.getCount())
                                .createTime(LocalDateTime.now())
                                .updateTime(LocalDateTime.now())
                                .build());
            });
            //删除对应购物车商品
            carService.remove(new LambdaQueryWrapper<CartItem>()
                    .eq(CartItem::getUserId, DentalUtils.getUserId())
                    .in(CartItem::getSkuId, cartVoList.stream().map(CartVo::getSkuId).collect(Collectors.toList()))
            );
            orderSkuService.saveBatch(orderSkuList);
        }
        //计算总金额；
        reduce = orderSkuList.stream()
                .map(orderSku -> orderSku.getCurPrice().multiply(BigDecimal.valueOf(orderSku.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        postFee = BigDecimal.valueOf(10);
        if(reduce.compareTo(BigDecimal.valueOf(20)) > 0){
            postFee = BigDecimal.valueOf(0);
        }
        OrderInfo build = OrderInfo.builder()
                .orderId(order.getId())
                .orderState(1)
                .countdown(5 * 60)//默认设置为5分钟
                .skusId(skuids)
                .receiverAddress(address.getFullLocation() + address.getAddress())
                .receiverContact(address.getReceiver())
                .receiverMobile(address.getContact())
                .totalMoney(reduce)
                .postFee(postFee)
                .payMoney(reduce.add(postFee))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        orderInfoService.save(build);
        return R.ok(order.getId());
    }

    /**
     *  获取订单详情
     * @param id 订单id
     * @return OrderResultVo
     */
    @GetMapping("/{id}")
    @Operation(summary="获取订单详情")
    public R getOrderInfo(@PathVariable("id") Long id){
        OrderInfo orderInfo = orderInfoService.getOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId, id)
        );
        List<OrderSku> list = orderSkuService.list(new LambdaQueryWrapper<OrderSku>()
                .eq(OrderSku::getOrderId, orderInfo.getOrderId())
                .in(OrderSku::getSkuId, orderInfo.getSkusId())
        );
        OrderResultVo orderResultVo = new OrderResultVo();
        BeanUtils.copyProperties(orderInfo,orderResultVo);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createTime = orderInfo.getCreateTime().plusMinutes(5);
        long seconds = Duration.between(now, createTime).getSeconds();
        if(orderInfo.getOrderState() == 1 && orderInfo.getCountdown() > 0 ){
            orderResultVo.setCountdown((int) seconds);
        }
        if(orderInfo.getOrderState() == 1 && seconds <= 0){
            orderInfo.setCountdown(-1);
            orderInfo.setOrderState(6);
            orderInfoService.updateById(orderInfo);
        }
        orderResultVo.setSkus(
                list.stream().map(orderSku -> OrderSkuVo.builder()
                        .id(orderSku.getId())
                        .spuId(orderSku.getSpuId())
                        .name(orderSku.getName())
                        .attrsText(orderSku.getAttrsText())
                        .quantity(orderSku.getQuantity())
                        .curPrice(orderSku.getCurPrice())
                        .image(orderSku.getImage())
                        .build()
                ).collect(Collectors.toList())
        );
        //构造返回参数
        return R.ok(orderResultVo);
    }

    @Autowired
    OrderMapper orderMapper;
    @GetMapping("")
    @Operation(summary="获取订单列表")
    public R getOrderList(@RequestParam Integer orderState,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize){
//        TODO:这个地方感觉有bug,应该是分页查询没有成功
        //1.根据订单id 左连接查询订单详情表中的orderState---做分页---在去查询ordersku表中的信息
        Long userId = DentalUtils.getUserId();
        List<OrderResultVo> orderResultVo = orderMapper.selectOrderState(userId,orderState);
        Page<OrderListResult> objects = PageHelper.startPage(page, pageSize);
        OrderListResult orderListResult = new OrderListResult();
        List<OrderItem> items = new ArrayList<>();
        orderResultVo.forEach(orderResultVo1 -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderResultVo(orderResultVo1);
            orderItem.setTotalNum(orderResultVo1.getSkus().size());
            items.add(orderItem);
        });
        //获取对应的分页参数
        // 4. 获取分页信息（使用PageInfo包装查询结果）
        orderListResult.setItems(items);
        orderListResult.setPage(objects.getPages());
        orderListResult.setPages(objects.getPageNum());
        orderListResult.setPageSize(objects.getPageSize());
        return R.ok(orderListResult);
    }


    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消订单")
    public R cancelOrder(@PathVariable("id")Long id,@RequestBody CancelDto dto){
        //根据订单id修改对应的商品详情页
        boolean update = orderInfoService.update(new LambdaUpdateWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId, id)
                .set(OrderInfo::getOrderState, 6)
                .set(OrderInfo::getCancelReason, dto.getCancelReason())
        );
        return getOrderInfo(id);
    }

    @DeleteMapping("")
    @Operation(summary = "删除订单")
    @Transactional
    public R delete(@RequestBody IdsDto ids){
        List<Integer> allowedOrderStates = Arrays.asList(4, 5, 6);
        //找到可以删除的
        List<OrderInfo> list = orderInfoService.list(new LambdaQueryWrapper<OrderInfo>()
                .in(OrderInfo::getOrderId, ids.getIds())
        );
        //过滤掉不允许删除的
        list = list.stream().filter(orderInfo -> allowedOrderStates.contains(orderInfo.getOrderState())).collect(Collectors.toList());
        if(list.size() == 0){
            return R.fail("订单状态不允许删除");
        }
        List<Long> collect = list.stream().map(OrderInfo::getOrderId).collect(Collectors.toList());
        orderSkuService.remove(new LambdaQueryWrapper<OrderSku>()
                .in(OrderSku::getOrderId, collect)
        );
        orderInfoService.remove(new LambdaQueryWrapper<OrderInfo>()
                .in(OrderInfo::getOrderId, collect)
        );
        orderIService.remove(new LambdaQueryWrapper<Order>()
                .in(Order::getId, collect)
        );
        return R.ok();
    }


    @Operation(summary = "确认收货")
    @PutMapping("/{id}/receipt")
    public R receiptOrder(@PathVariable("id") Long id){
        boolean update = orderInfoService.update(new LambdaUpdateWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId, id)
                .eq(OrderInfo::getOrderState, 3)
                .set(OrderInfo::getOrderState, 4)
        );
        return R.ok();
    }


    @GetMapping("/repurchase/{id}")
    @Operation(summary = "再次购买")
    public R Repurchase(@PathVariable("id") Long id){
        //根据订单id查找对应的sku
        OrderInfo one = orderInfoService.getOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderId, id)
        );
        Address address = new Address();
        address.setFullLocation(one.getReceiverAddress());
        address.setContact(one.getReceiverMobile());
        address.setReceiver(one.getReceiverContact());
        ArrayList<Address>addressList = new ArrayList<>();
        addressList.add(address);
        List<OrderPreGoods> OrderPreGoodsList = new ArrayList<>();
        //查找对应的sku
        List<OrderSku> list = orderSkuService.list(new LambdaQueryWrapper<OrderSku>()
                .eq(OrderSku::getOrderId, id)
        );
        list.forEach(orderSku -> {
            OrderPreGoods build = OrderPreGoods.builder()
                    .id(orderSku.getId().intValue())
                    .name(orderSku.getName())
                    .picture(orderSku.getImage())
                    .payPrice(orderSku.getCurPrice())
                    .price(orderSku.getCurPrice())
                    .count(orderSku.getQuantity())
                    .skuId(orderSku.getSkuId())
                    .attrsText(orderSku.getAttrsText())
                    .totalPayPrice(orderSku.getCurPrice().multiply(BigDecimal.valueOf(orderSku.getQuantity())))
                    .build();
            OrderPreGoodsList.add(build);
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

        return  R.ok(PreOrderVo.builder()
                .userAddresses(addressList)
                .goods(OrderPreGoodsList)
                .summary(summary)
                .build());
    }


    @Autowired
    GoodsSkusService goodsSkusService;
    @Autowired
    GoodsParticularsMapper goodsPartsMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    SkusSpecService skusSpecService;
    @GetMapping("/pre/now")
    @Operation(summary = "获取立即购买订单")
    public R OrderPreNow(@RequestParam Long skuId,@RequestParam Long count ,@RequestParam(defaultValue = "-1") Long addressId){
        LambdaQueryWrapper<Address> eq = new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, DentalUtils.getUserId());
        if(Long.compare(addressId, -1L) != 0){
                eq.eq(Address::getId, addressId);
        }
        //1、获取用户的地址列表
        List<Address> addressList = addressService.list(eq);
        //2、获取用户的选择的商品列表
        ArrayList<OrderPreGoods> OrderPreGoodsList = new ArrayList<>();
        GoodsSkus sku = goodsSkusService.getById(skuId);
        StringBuilder attrsText = new StringBuilder();
        GoodsSkus goodsSkus = goodsSkusService.getById(sku.getSkuId());
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
        Goods goods = goodsService.getById(one.getGoodsId());
        OrderPreGoods build = OrderPreGoods.builder()
                .name(goods.getGoodsName())
                .picture(sku.getSkuPicture())
                .payPrice(new BigDecimal(sku.getSkuPrice()))
                .price(new BigDecimal(sku.getSkuOldPrice()))
                .count(count.intValue())
                .skuId(Long.valueOf(sku.getSkuId()))
                .attrsText(attrsText.toString())
                .totalPayPrice(new BigDecimal(sku.getSkuPrice()).multiply(BigDecimal.valueOf(count)))
                .build();
        OrderPreGoodsList.add(build);
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
