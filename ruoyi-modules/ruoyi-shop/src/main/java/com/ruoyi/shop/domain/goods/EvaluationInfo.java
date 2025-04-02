package com.ruoyi.shop.domain.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationInfo {
    @TableId(type = IdType.AUTO)
    private Integer evaluationInfoId;
    private String orderInfo;
    private Integer evaluationInfoMember;
    private Double evaluationInfoScore;
    private String evaluationInfoTags;
    private String evaluationInfoContent;
    private String evaluationInfoPictures;
    private String officialReply;
    private Integer praiseCount;
    private LocalDateTime createTime;
    private Double praisePercent;
}
