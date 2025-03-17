package com.ruoyi.shop.domain.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class Hot {
    @TableId(type = IdType.AUTO)
    private Integer hotId;
    private Integer hotPicturesId;
    private String hotTitle;
    private String hotAlt;
    private Integer hotTarget;
    private Integer hotType;
}
