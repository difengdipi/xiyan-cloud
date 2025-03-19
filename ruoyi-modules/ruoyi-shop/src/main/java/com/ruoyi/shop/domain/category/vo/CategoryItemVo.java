package com.ruoyi.shop.domain.category.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemVo {

    @JsonProperty("icon")
    private String iconUrl;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;
}
