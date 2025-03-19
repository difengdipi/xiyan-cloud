package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GoodsProperty {
    @TableId(type = IdType.AUTO)
    private Integer propertyId;
    private String propertyName;
    private String propertyValue;
}
