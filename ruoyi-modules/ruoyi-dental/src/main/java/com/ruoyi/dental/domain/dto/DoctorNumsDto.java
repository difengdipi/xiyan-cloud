package com.ruoyi.dental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 统计医生的预约量
 * @author: zh
 * @Create : 2025/4/25
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorNumsDto {
    //医生id
    private  Long id;
    //对应的方法
    private type  fun;
    public enum type{
        //预约量
        add,cancel
    }
}
