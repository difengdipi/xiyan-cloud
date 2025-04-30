package com.ruoyi.system.api.SmsApi.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.SmsApi.RemoteSmsService;
import com.ruoyi.system.api.constants.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class RemoteSmsFallbackFactory implements FallbackFactory<RemoteSmsService> {
    @Override
    public RemoteSmsService create(Throwable cause) {
        log.error("调用短信服务失败:{}",cause.getMessage());
        return new RemoteSmsService() {
            @Override
            public R sendUserApp(String phone, SmsRequest request, String source) {
                return R.fail("发送短信失败:" + cause.getMessage());
            }
        };
    }
}
