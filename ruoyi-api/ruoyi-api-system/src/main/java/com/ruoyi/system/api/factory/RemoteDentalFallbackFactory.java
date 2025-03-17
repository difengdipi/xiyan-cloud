package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDentalService;
import com.ruoyi.system.api.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/15
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
public class RemoteDentalFallbackFactory  implements FallbackFactory<RemoteDentalService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDentalFallbackFactory.class);

    @Override
    public RemoteDentalService create(Throwable cause) {
        log.error("调用牙医系统失败:{}",cause.getMessage());
        return new RemoteDentalService() {
            @Override
            public R insertBySysUser(SysUser sysUser, String source) {
                return R.fail("创建医生对象失败:" + cause.getMessage());
            }
        };
    }
}
