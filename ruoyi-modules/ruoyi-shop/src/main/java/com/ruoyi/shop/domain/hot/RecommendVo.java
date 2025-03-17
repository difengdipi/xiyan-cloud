package com.ruoyi.shop.domain.hot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendVo {
    private Integer recommendId;
    private String bannerPicture;
    private String recommendTitle;
    private List< TypeVo > typeList;
}
