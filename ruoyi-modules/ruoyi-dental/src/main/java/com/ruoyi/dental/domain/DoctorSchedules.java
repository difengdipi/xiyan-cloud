package com.ruoyi.dental.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data // Lombok注解，用于自动生成getter、setter、toString等方法
@TableName("tb_doctor_schedules")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSchedules {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long doctorId;
    // 预约日期
    @JsonProperty("date")
    // 统一使用java.util.Date
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Integer status; // 可预约类型（0 不可预约, 1 上午可预约, 2 下午可预约, 3 全天可约）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer appNum = 0;
}