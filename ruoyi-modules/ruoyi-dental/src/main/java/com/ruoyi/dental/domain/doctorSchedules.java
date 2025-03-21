package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data // Lombok注解，用于自动生成getter、setter、toString等方法
@TableName("tb_doctor_schedules")
public class doctorSchedules {

    @Id // 标识主键
    private Long id;

    private Long doctorId;
    // 预约日期
    @JsonProperty("date")
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private Integer status; // 可预约类型（0 不可预约, 1 上午可预约, 2 下午可预约, 3 全天可约）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}