package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.factory.RemoteDentalFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/15
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@FeignClient(contextId = "remoteDentalService", value = ServiceNameConstants.DENTAL_SERVICE, fallbackFactory = RemoteDentalFallbackFactory.class)
public interface RemoteDentalService {
    @PostMapping("/doctors/insert/sysUser")
    public R insertBySysUser(@RequestBody SysUser sysUser,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
