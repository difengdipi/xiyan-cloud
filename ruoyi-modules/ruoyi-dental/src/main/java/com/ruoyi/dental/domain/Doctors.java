package com.ruoyi.dental.domain;

import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 医生信息对象 tb_doctors
 *
 * @author zh
 * @date 2025-04-09
 */
@Data
public class Doctors
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联的用户id */
    @Excel(name = "医生工号")
    private Long userId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String name;

    /** 医生状态（0  请假 1 营业中 2 下班） */
    @Excel(name = "医生状态", readConverterExp = "0=请假,1=营业中,2=下班")
    private Long status;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 职称 */
    @Excel(name = "职称")
    private String title;

    /** 所属医院 */
    @Excel(name = "所属医院")
    private String hospital;

    /** 专业特长 */
    @Excel(name = "专业特长")
    private String specialty;

    /** 详细介绍 */
    @Excel(name = "详细介绍")
    private String detail;

    /** 总预约数量 */
    @Excel(name = "总预约数量")
    private Long appNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
