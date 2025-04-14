package com.ruoyi.dental.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 治疗计划对象 tb_treatment_plans
 * 
 * @author zh
 * @date 2025-04-10
 */
@Data
public class TreatmentPlans extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long planId;

    /** 关联患者 */
    @Excel(name = "关联患者")
    private Long patientId;
    @Excel(name = "药品单")
    private String skusId;
    /** 预约号 */
    @Excel(name = "预约号")
    private Long userAppId;
    @Excel(name = "治疗医生id")
    private Long doctorId;

    /** 病例图片 */
    @Excel(name = "病例图片")
    private String treatmentPicture;

    /** 治疗内容 */
    @Excel(name = "治疗内容")
    private String treatmentPlan;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public Long getPatientId() 
    {
        return patientId;
    }

    public void setUserAppId(Long userAppId) 
    {
        this.userAppId = userAppId;
    }

    public Long getUserAppId() 
    {
        return userAppId;
    }

    public void setTreatmentPicture(String treatmentPicture) 
    {
        this.treatmentPicture = treatmentPicture;
    }

    public String getTreatmentPicture() 
    {
        return treatmentPicture;
    }

    public void setTreatmentPlan(String treatmentPlan) 
    {
        this.treatmentPlan = treatmentPlan;
    }

    public String getTreatmentPlan() 
    {
        return treatmentPlan;
    }

    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

}
