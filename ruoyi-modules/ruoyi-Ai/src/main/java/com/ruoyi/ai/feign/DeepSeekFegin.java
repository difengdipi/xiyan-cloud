package com.ruoyi.ai.feign;

import com.ruoyi.ai.pojos.ChatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/12
 * @Project_name : spring-Ai
 * @Version :
 **/
@Component
@FeignClient(name = "deepseek",url="https://api.deepseek.com/v1")
public interface DeepSeekFegin {

    @PostMapping("/chat/completions")
    public String getContent(@RequestHeader("Authorization") String authorization, @RequestBody ChatRequest chatRequest);
}
