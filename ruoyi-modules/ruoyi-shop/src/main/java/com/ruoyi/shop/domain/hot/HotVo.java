package com.ruoyi.shop.domain.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotVo {
    @TableId(type = IdType.AUTO)
    private Integer hotId;
    private String[] hotPictures;
    private String hotTitle;
    private String hotAlt;
    private Integer hotTarget;
    private Integer hotType;
}
