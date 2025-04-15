package com.ruoyi.shop.domain.order.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:  订单详情 返回信息
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderResultVo {
    /**
     * 订单编号
     */
    private Long id;

    /**
     * 订单状态：
     * 1 为待付款、2 为待发货、3 为待收货、4 为待评价、5 为已完成、6 为已取消
     */
    private Integer orderState;

    /**
     * 倒计时--剩余的秒数
     * -1 表示已经超时，正数表示倒计时未结束
     */
    private Integer countdown;

    /**
     * 商品集合 [ 商品信息 ]
     */
    private List<OrderSkuVo> skus;

    /**
     * 收货人
     */
    private String receiverContact;

    /**
     * 收货人手机
     */
    private String receiverMobile;

    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 快递单号
     */
    private String trackingNumber;
    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 商品总价（单位：分）
     */
    private BigDecimal totalMoney;

    /**
     * 运费（单位：分）
     */
    private BigDecimal postFee;

    /**
     * 应付金额（单位：分）
     */
    private BigDecimal payMoney;

}
