package com.ruoyi.ai.pojos;

import lombok.Data;

/**
 * @Description: ai的类型
 * @author: zh
 * @Create : 2025/3/27
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Data
public class AiType {
    private String type;
    private String name;
    private String description;
    private String icon;
    //模型
    private String model;
    private String modelName;
}
