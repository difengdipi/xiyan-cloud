package com.ruoyi.shop.domain.order.dto;

import lombok.Data;

/**
 * @Description:
 * @author: zh  订单列表参数
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class OrderListDto {
    private Integer orderState;
    /** 页码：默认值为 1 */
    private Integer  page = 1;
    /** 页大小：默认值为 10 */
    private Integer  pageSize = 10;
}
