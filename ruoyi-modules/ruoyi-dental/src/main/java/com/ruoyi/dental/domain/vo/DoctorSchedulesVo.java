package com.ruoyi.dental.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data // Lombok注解，用于自动生成getter、setter、toString等方法
public class DoctorSchedulesVo extends BaseEntity {
    /** 用户ID */
    private Long id;
    private Long doctorId;
    @Excel(name = "医生工号", type = Excel.Type.ALL, cellType = Excel.ColumnType.NUMERIC, prompt = "工号")
    @JsonProperty("userId")
    private Long userId;
    @Excel(name = "医生姓名", type = Excel.Type.ALL)
    private String doctorName;
    private Long deptId;
    // 预约日期
    private String cancelReason;
    // 预约日期
    @JsonProperty("date")
    @DateTimeFormat (pattern = "yyyy/MM/dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy/MM/dd", type = Excel.Type.ALL)
    private Date date;
    /** 可预约类型（0  不可预约   1 上午可预约  2 下午可预约  3 全天可约） */
    @Excel(name = "可预约类型", readConverterExp = "0=不可预约,1=上午可预约,2=下午可预约,3=全天可约", type = Excel.Type.ALL ,prompt="不可预约,上午可预约,下午可预约,全天可约")
    private Integer status; // 可预约类型（0 不可预约, 1 上午可预约, 2 下午可预约, 3 全天可约）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Excel(name = "最大预约数量", type = Excel.Type.ALL,cellType = Excel.ColumnType.NUMERIC)
    private Integer maxNum;

    @Excel(name = "预约数量", type = Excel.Type.EXPORT,cellType = Excel.ColumnType.NUMERIC)
    private Integer appNum;

}