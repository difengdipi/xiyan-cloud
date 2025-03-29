package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.RemoteAuthIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class RemoteAuthFallbackFactory implements FallbackFactory<RemoteAuthIService> {
    @Override
    public RemoteAuthIService create(Throwable cause) {
        log.error("调用认证服务失败:{}",cause.getMessage());
        return new RemoteAuthIService() {
            @Override
            public Long getUserId() {
                return null;
            }
        };
    }
}
