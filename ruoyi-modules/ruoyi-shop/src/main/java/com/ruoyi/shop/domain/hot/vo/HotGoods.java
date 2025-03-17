package com.ruoyi.shop.domain.hot.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.shop.domain.goods.Goods;
import com.ruoyi.shop.domain.hot.Hot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class HotGoods {
    @TableId(type = IdType.AUTO)
    private Integer hotGoodsId;
    @TableField(value = "hot_id")
    private Integer HotId;
    @TableField(value = "goods_id")
    private Integer goodsId;
    // 下面这两个字段不会直接映射到数据库表中，而是用来存储关联对象的信息
    @TableField(exist = false) // 表示该字段不在数据库表中存在
    private Hot hot;

    @TableField(exist = false) // 表示该字段不在数据库表中存在
    private Goods goods;
}
