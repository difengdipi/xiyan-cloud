package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 医生信息对象 tb_doctors
 * 
 * @author zh
 * @date 2025-03-15
 */
@Data
@TableName("tb_doctors")
public class Doctors {

    @Id // 标识主键
    private Long id;

    private Long userId;

    private String name;

    private Integer status; // 注意这里用Integer而不是int，允许存储null值

    private String avatar;

    private String title;

    private String hospital;

    private String specialty;
    //总预约数
    private String appNum;

    private String detail;

    private Date createTime;

    private Date updateTime;
}