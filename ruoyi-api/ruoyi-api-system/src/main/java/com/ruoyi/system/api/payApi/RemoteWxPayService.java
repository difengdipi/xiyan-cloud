package com.ruoyi.system.api.payApi;

import com.ruoyi.system.api.factory.RemoteWxPayFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/24
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@FeignClient(contextId = "remoteWxPayService",name = "remoteWxPayService",url = "https://api.weixin.qq.com" ,fallbackFactory = RemoteWxPayFallbackFactory.class )
public interface RemoteWxPayService {
}
