package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 跟进任务对象 tb_follow_up_tasks
 * 
 * @author zh
 * @date 2025-03-15
 */
@TableName("tb_follow_up_tasks")
public class FollowUpTasks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识跟进任务的编号 */
    private Long taskId;

    /** 关联的患者ID */
    @Excel(name = "关联的患者ID")
    private Long patientId;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String description;

    /** 任务截止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务截止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 任务状态：0-待处理, 2-进行中 ，3-已完成 */
    @Excel(name = "任务状态：0-待处理, 2-进行中 ，3-已完成")
    private Long status;

    /** 记录创建时间 */
    private Date createdAt;

    /** 记录最后更新时间 */
    private Date updatedAt;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public Long getPatientId() 
    {
        return patientId;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDueDate(Date dueDate) 
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate() 
    {
        return dueDate;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("patientId", getPatientId())
            .append("description", getDescription())
            .append("dueDate", getDueDate())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
