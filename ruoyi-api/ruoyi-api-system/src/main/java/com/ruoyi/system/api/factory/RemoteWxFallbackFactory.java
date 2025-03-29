package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.RemoteWxService;
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
@Slf4j
@Component
public class RemoteWxFallbackFactory implements FallbackFactory<RemoteWxService> {
    @Override
    public RemoteWxService create(Throwable cause) {
        log.error("调用微信服务失败：{}",cause);
        return new RemoteWxService(){
            @Override
            public String getAccess(String appid, String secret, String js_code, String grant_type) {
                log.info("调用微信服务获取getAccess失败：{}",cause);
                return null;
            }

            @Override
            public String getoauth2(String appid, String secret, String code, String grant_type) {
                log.info("调用微信服务获取getoauth2失败：{}",cause);
                return null;
            }

            @Override
            public String getWxAccessToken(String grant_type, String appid, String secret) {
                log.info("调用微信服务获取access_token失败：{}",cause);
                return null;
            }

            @Override
            public String getUserInfo(String access_token, String openid, String lang) {
                log.info("调用微信服务获取agetUserInfo失败:{}",cause);
                return null;
            }
        };
    }
}
