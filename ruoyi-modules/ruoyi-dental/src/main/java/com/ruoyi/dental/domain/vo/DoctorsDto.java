package com.ruoyi.dental.domain.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/9
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class DoctorsDto {

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
