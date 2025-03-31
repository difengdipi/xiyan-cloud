package com.ruoyi.shop.domain.cart.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class DeCartDto {
    private List<Long> ids;
    private Boolean selected;
}
