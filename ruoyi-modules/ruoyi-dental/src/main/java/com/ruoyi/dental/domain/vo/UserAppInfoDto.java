package com.ruoyi.dental.domain.vo;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/10
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class UserAppInfoDto extends BaseEntity {
    @Excel(name = "医生工号", type = Excel.Type.ALL, cellType = Excel.ColumnType.NUMERIC, prompt = "工号")
    private Long id; // 使用Long类型以匹配数据库中的bigint(20)
    private Long userId; // 使用Integer匹配int(11)
    private Long doctorId; // 使用Long类型以匹配数据库中的bigint(20)
    @Excel(name = "医生姓名", type = Excel.Type.ALL,prompt = "医生姓名")
    private String doctorName;// 医生姓名
    private Long scheduleId; //预约表id
    private Long patientId; ////患者表id
    @Excel(name = "预约日期", type = Excel.Type.ALL,prompt = "预约日期")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date scheduleDate; // 预约日期
    private String name;
    @Excel(name = "预约状态", type = Excel.Type.ALL,readConverterExp = "0=预约成功,1=已完成,2=取消", prompt = "预约状态")
    private Short status ; // 跟踪预约状态（0  预约成功 1  已完成  2 取消  ）
    @Excel(name = "医生工号", type = Excel.Type.ALL, readConverterExp = "1=下午,2=下午", prompt = "预约状态")
    private Short appStatus ; // 1 下午 2 下午
    @Excel(name = "电话", type = Excel.Type.ALL, prompt = "电话")
    private String phone; // 如果电话号码可能超过int范围，请考虑使用String类型
    private String idcard; // 预约人的身份证号
    private String cancelReason;// 取消原因
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
