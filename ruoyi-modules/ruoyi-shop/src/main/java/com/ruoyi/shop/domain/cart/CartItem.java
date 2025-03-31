package com.ruoyi.shop.domain.cart;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data // Lombok annotation for generating all the getters/setters, toString, etc.
@NoArgsConstructor // Generates a constructor with no parameters
@AllArgsConstructor // Generates a constructor with all parameters
@Builder // Builder pattern for creating instances of this class
@TableName("sys_cart_item")
@Getter
@Setter
public class CartItem {

    @Id // Specifies the primary key of the entity
    private Long id; // 商品 ID

    private Long skuId; // SKU ID

    private Long userId; // 下单用户 ID

    private String name; // 商品名称

    private String picture; // 图片

    private Integer count; // 数量，默认值为0

    private BigDecimal price; // 加入时价格

    private BigDecimal nowPrice; // 当前的价格

    private Integer stock; // 库存，默认值为0

    private Boolean selected; // 是否选中，默认值为FALSE

    private String attrsText; // 属性文字

    private Boolean isEffective; // 是否为有效商品，默认值为TRUE

    private Timestamp createdTime; // 创建时间

    private Timestamp updatedTime; // 更新时间
}