package com.ruoyi.shop.domain.order.vo;

import com.ruoyi.shop.domain.address.Address;
import com.ruoyi.shop.domain.order.dto.OrderPreGoods;
import com.ruoyi.shop.domain.order.dto.Summary;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 获取预付订单 返回信息
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 用户地址列表 [ 地址信息 ] */
    private List<Address> userAddresses;
    /** 商品集合 [ 商品信息 ] */
    private List<OrderPreGoods> goods;
    /** 结算信息 */
    private Summary summary;
}
