package com.ruoyi.dental.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/24
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreatmentPlansVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long planId;

    /** 关联患者 */
    @Excel(name = "关联患者")
    private Long patientId;
    ///患者姓名
    private String name ;
    //患者手机号
    private String phone;
    @Excel(name = "药品单")
    private String skusId;
    /** 预约号 */
    @Excel(name = "预约号")
    private Long userAppId;
    @Excel(name = "治疗医生id")
    @JsonProperty("doctorId")
    private Long doctorId;

    /** 病例图片 */
    @Excel(name = "病例图片")
    private String treatmentPicture;

    /** 治疗内容 */
    @Excel(name = "治疗内容")
    private String treatmentPlan;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
