package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 患者列表对象 tb_patients
 * 
 * @author zh
 * @date 2025-03-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_Patients")
@Builder
public class Patients
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识患者的编号 */
    @TableId(type = IdType.AUTO)
    private Long patientId;

    /** 患者名字 */
    @Excel(name = "患者名字")
    private String userName;

    /** 性别 */
    @Excel(name = "性别")
    private int gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phoneNumber;

    /** 初诊日期 */
    private Date createdTime;

    /** 记录最后更新时间 */
    private Date updatedTime;
}
