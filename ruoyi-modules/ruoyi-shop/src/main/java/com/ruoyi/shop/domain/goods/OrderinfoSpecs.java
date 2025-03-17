package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderinfoSpecs {
    @TableId(type = IdType.AUTO)
    private Integer orderInfoId;
    private String orderInfoSpecs;
    private Integer orderInfoQuantity;
    private Date createTime;
}
