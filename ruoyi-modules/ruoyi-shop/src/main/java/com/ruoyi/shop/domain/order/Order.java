package com.ruoyi.shop.domain.order;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单主键
     */
    private Long id;

    /**
     * 所属用户 ID
     */
    private Long userId;

    /**
     * 收货地址 ID
     */
    private Long addressId;

    /**
     * 配送时间类型：
     * 1 为不限，2 为工作日，3 为双休或假日
     */
    private short deliveryTimeType;

    /**
     * 订单备注
     */
    private String buyerMessage;

    /**
     * 支付渠道：
     * 1 支付宝、2 微信（支付方式为在线支付时传值，货到付款时不传值）
     */
    private short payChannel;

    /**
     * 支付方式：
     * 1 为在线支付，2 为货到付款
     */
    private short payType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
