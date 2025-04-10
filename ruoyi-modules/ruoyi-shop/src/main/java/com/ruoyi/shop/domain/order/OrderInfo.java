package com.ruoyi.shop.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:  订单详情
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@TableName(value = "sys_order_info")
public class OrderInfo{

    /**
     * 订单详情编号
     */
    private Long id;
    /**
     *  订单编号
     */
    @Excel(name = "订单编号", type = Excel.Type.ALL, cellType = Excel.ColumnType.STRING, prompt = "订单编号")
    private Long orderId;

    /**
     * 订单状态：
     * 1 为待付款、2 为待发货、3 为待收货、4 为待评价、5 为已完成、6 为已取消
     */
    @Excel(name = "订单状态", readConverterExp = "2=待发货,3=待收货,4=待评价,5=已完成,6=已取消",type = Excel.Type.EXPORT, prompt = "1 为待付款、2 为待发货、3 为待收货、4 为待评价、5 为已完成、6 为已取消")
    private Integer orderState;
    /**快递单号*/
    @Excel(name = "快递单号",  type = Excel.Type.ALL)
    private String trackingNumber;
    /**
     * 倒计时--剩余的秒数
     * -1 表示已经超时，正数表示倒计时未结束
     */
    private Integer countdown;

    /**
     * 商品集合 [ 商品信息 ]
     */
    @Excel(name = "商品集合", type = Excel.Type.EXPORT)
    private List<String> skusId;

    /**
     * 收货人
     */
    @Excel(name = "收货人", type = Excel.Type.EXPORT)
    private String receiverContact;

    /**
     * 收货人手机
     */
    @Excel(name = "收货人手机号", type = Excel.Type.ALL)
    private String receiverMobile;

    /**
     * 收货人地址
     */
    @Excel(name = "收货人地址", type = Excel.Type.ALL)
    private String receiverAddress;

    /**
     * 商品总价（单位：分）
     */
    @Excel(name = "收货人地址", type = Excel.Type.ALL)
    private BigDecimal totalMoney;

    /**
     * 运费（单位：分）
     */
    @Excel(name = "运费（单位：分）",  type = Excel.Type.ALL)
    private BigDecimal postFee;

    /**
     * 应付金额（单位：分）
     */
    @Excel(name = "应付金额", type = Excel.Type.ALL)
    private BigDecimal payMoney;
    @Excel(name = "取消原因", type = Excel.Type.ALL)
    private String cancelReason;

    /**
     * 更新时间时间
     */
    /**
     * 下单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
