package com.ruoyi.system.api.SmsApi;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.SmsApi.factory.RemoteSmsFallbackFactory;
import com.ruoyi.system.api.constants.SmsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description:  远程短信服务
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@FeignClient(contextId = "remoteSmsService", value = ServiceNameConstants.SMS_SERVICE, fallbackFactory = RemoteSmsFallbackFactory.class)
public interface RemoteSmsService {
    /**
     * 给用户手机号发送就诊提示
     * @param phone
     * @param request
     * @param source
     * @return
     */
    @PostMapping("/user/sendUserApp/{phone}")
    public R sendUserApp(@PathVariable("phone") String phone, @RequestBody SmsRequest request , @RequestHeader(SecurityConstants.FROM_SOURCE) String source) ;
}