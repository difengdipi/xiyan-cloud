package com.ruoyi.system.api;

import com.ruoyi.system.api.factory.RemoteAuthFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@FeignClient(name = "ruoyi-auth", value = "ruoyi-auth",fallbackFactory = RemoteAuthFallbackFactory.class)
public interface RemoteAuthIService {
    /**
     * 解析用户数据--非若依环境客户端懒得写了
     * @param
     * @return
     */
    @GetMapping("/Common/getUserId")
    public Long getUserId();
}
