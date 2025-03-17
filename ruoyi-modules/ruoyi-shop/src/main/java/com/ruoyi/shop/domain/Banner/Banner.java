package com.ruoyi.shop.domain.Banner;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @TableId(type = IdType.AUTO)
    private Integer bannerId;
    private String imgUrl;
    private String hrefUrl;
    private Integer bannerType;
}
