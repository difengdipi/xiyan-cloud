package com.ruoyi.shop.domain.goods.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class GoodsDto {
        private static final long serialVersionUID = 1L;
        @TableId(type = IdType.AUTO)
        private Integer goodsId;

        @TableField("category_id")
        private Long categoryId;
        private String categoryname;
        private String goodsName;
        private String goodsDesc;
        private Double goodsPrice;
        private String goodsPicture;
        private Integer goodsOrdernum;
}
