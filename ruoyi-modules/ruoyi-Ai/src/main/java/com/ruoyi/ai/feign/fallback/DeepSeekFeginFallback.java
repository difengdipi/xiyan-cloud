package com.ruoyi.ai.feign.fallback;

import com.ruoyi.ai.feign.DeepSeekFegin;
import com.ruoyi.ai.pojos.ChatRequest;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/27
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class DeepSeekFeginFallback implements FallbackFactory<DeepSeekFegin> {

    @Override
    public DeepSeekFegin create(Throwable cause) {
        log.error("调用DeepSeek系统失败:{}",cause.getMessage());
        return new DeepSeekFegin() {
            @Override
            public R getContent(String authorization, ChatRequest chatRequest) {
                return R.fail("调用DeepSeek系统失败:" + cause.getMessage());
            }
        };
    }
}
