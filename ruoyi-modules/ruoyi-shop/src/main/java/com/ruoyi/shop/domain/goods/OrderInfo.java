package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    @TableId(type = IdType.AUTO)
    private Integer orderInfoId;
    private Integer orderSpecId;
    private String orderInfoMember;
    private Integer orderInfoScore;
    private String orderInfoTags;
    private String orderInfoContent;
    private String orderInfoPictures;
    private String officialReply;
    private Integer praiseCount;
    private Double praisePercent;
    private Date createTime;
}
