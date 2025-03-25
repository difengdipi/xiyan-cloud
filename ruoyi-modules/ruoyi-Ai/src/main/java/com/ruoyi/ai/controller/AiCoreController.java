package com.ruoyi.ai.controller;


import com.ruoyi.ai.component.OpenChatModel;
import com.ruoyi.ai.feign.DeepSeekFegin;
import com.ruoyi.ai.pojos.ChatRequest;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.Util.DentalUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/2/26
 * @Project_name : spring-Ai
 * @Version :
 **/
@RestController
@RequestMapping("/v1")
@Tag(name = "Ai服务请求v1接口")
public class AiCoreController {

    @Autowired
    DeepSeekFegin deepSeekFegin;
    @Autowired
    OpenChatModel openChatModel;
    /**
     *
     * @param model  选择的模型
     * @param msg  请求的消息
     * @return
     */
    @Operation(summary = "请求")
    @PostMapping("/get")
    public R getContent(@RequestHeader("model")String model,@RequestBody String msg){
        ChatRequest.Message message = ChatRequest.Message.builder()
                .content(msg)
                .role(DentalUtils.getUserId().toString()).build();
        ChatRequest build = ChatRequest.builder()
                .model(model)
                .messages(Arrays.asList(message))
                .stream(false)
                .build();
        return R.ok(deepSeekFegin.getContent(openChatModel.getDpApiKey(), build));
    }
}
