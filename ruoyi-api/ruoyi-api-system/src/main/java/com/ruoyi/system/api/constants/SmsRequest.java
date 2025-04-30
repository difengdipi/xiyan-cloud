package com.ruoyi.system.api.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {
    public Map<String, Object> param;
    public String type;
}
