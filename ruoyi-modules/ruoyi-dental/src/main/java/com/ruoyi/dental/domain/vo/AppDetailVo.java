package com.ruoyi.dental.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppDetailVo {

    // 预约ID
    private Long id;

    // 医生头像地址
    private String avatar;

    // 预约用户的名字
    private String name;

    // 预约医生的名字
    private String doctorName;

    // 预约医生所属医院
    private String hospital;

    // 预约的状态
    private Short status;
    private Short appStatus;
    // 用户电话
    private Long phone;

    // 用户身份证号
    private String idcard;
    private String cancelReason;

    // 预约时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
}

