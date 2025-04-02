package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:  提交订单参数
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class OrderDto {
    /**
     * 收货地址ID
     */
    private Long addressId;
    /** 配送时间类型，1为不限，2为工作日，3为双休或假日 */
    private short deliveryTimeType;
    /** 订单备注 */
    private String buyerMessage;
    /** 商品集合[ 商品信息 ] */
    List<CartVo> goods;
    /** 支付渠道：支付渠道，1支付宝、2微信--支付方式为在线支付时，传值，为货到付款时，不传值 */
    private short payChannel;
    /** 支付方式，1为在线支付，2为货到付款 */
    private short payType = 1;
}
