package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.payApi.RemoteWxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/24
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@Component
@Slf4j
public class RemoteWxPayFallbackFactory implements FallbackFactory<RemoteWxPayService> {
    @Override
    public RemoteWxPayService create(Throwable cause) {
        log.info("调用微信支付远程接口失败:{}",cause.getMessage());
        return null;
    }
}
