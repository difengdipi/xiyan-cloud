package com.ruoyi.dental.domain;

import lombok.*;

import java.util.Date;

@Data // Lombok annotation for auto-generating getters, setters, equals, hash, and toString methods.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAppInfo {
    private Long id; // 使用Long类型以匹配数据库中的bigint(20)
    private Long userId; // 使用Integer匹配int(11)
    private Long doctorId; // 使用Long类型以匹配数据库中的bigint(20)
    private Long scheduleId; //预约表id
    private String name;
    private Short status = 0; // 跟踪预约状态（0  预约成功 1  已完成  2 取消  ）
    private Short appStatus ; // 1 下午 2 下午
    private Long phone; // 如果电话号码可能超过int范围，请考虑使用String类型
    private String idcard; // 预约人的身份证号
    private String cancelReason;// 取消原因
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

}