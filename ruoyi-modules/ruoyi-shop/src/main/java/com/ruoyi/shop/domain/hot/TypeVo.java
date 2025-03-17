package com.ruoyi.shop.domain.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeVo {
    @TableId(type = IdType.AUTO)
    private Integer typeId;
    private String typeTitle;
    private Map< String, Object > goodsItems;
}
