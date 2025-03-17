package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

/**
 * 医生信息对象 tb_doctors
 * 
 * @author zh
 * @date 2025-03-15
 */
@Data
@TableName("tb_doctors")
public class Doctors   {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 关联的用户id
     */
    private Long userId;

    /**
     * 医生姓名
     */
    @Excel(name = "医生姓名")
    private String name;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String title;

    /**
     * 所属医院
     */
    @Excel(name = "所属医院")
    private String hospital;

    /**
     * 专业特长
     */
    @Excel(name = "专业特长")
    private String specialty;

    /**
     * 详细介绍
     */
    @Excel(name = "详细介绍")
    private String detail;
}