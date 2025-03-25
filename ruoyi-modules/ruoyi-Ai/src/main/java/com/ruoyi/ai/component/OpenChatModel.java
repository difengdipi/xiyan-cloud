package com.ruoyi.ai.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/27
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@ConfigurationProperties
@Data
public class OpenChatModel {

    @Value("${deepseek.apiKey}")
    private String DpApiKey;

}
