package com.ruoyi.auth.config;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.auth.pojos.WxAccessToken;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteWxService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Configuration
@ConfigurationProperties
@Slf4j
@Lazy
public class WxConfig {

    @Resource
    RemoteWxService remoteWxService;
    @Autowired
    RedisService redisService;
    @Autowired
    RedissonClient redissonClient;
    private final static String Tokenkey = "wx_token:";
    private final static String Acckey = "wx_acckey:";
    @Value("${wechat.appid}")
    public String appid;
    @Value("${wechat.secret}")
    public String secret;
    public String grantType = "authorization_code";

    public String AcckeyGrantType = "client_credential";
    public final static Long expireTime = 500L;
    public String access_key;

    /**
     * 语言版本参数
     */
    public final static String langZhCN = "zh_CN";
    public final static String langZhTw = "zh_TW";
    public final static String langEn = "en";
    /**
     * 初始化构建微信配置--用于获取服务端的access_key
     */
    @PostConstruct
    public void init() {
        RLock lock = redissonClient.getLock(this.Acckey);
        try {
            if (lock.tryLock(5,60, TimeUnit.SECONDS)) {
                if (redisService.hasKey(Tokenkey) && redisService.getExpire(Tokenkey) > this.expireTime) {
                    this.access_key = redisService.getCacheObject(Tokenkey);
                    log.info("微信配置初始化成功");
                    return;
                }
                log.info("微信配置初始化开始");
                String AccessToken = remoteWxService.getWxAccessToken(this.AcckeyGrantType, this.appid, this.secret);
                WxAccessToken wxAccessToken = JSONObject.parseObject(AccessToken, WxAccessToken.class);

                this.access_key = wxAccessToken.getAccessToken();
                log.info("access_key为:{}", access_key);
                redisService.setCacheObject(Tokenkey, wxAccessToken.getAccessToken(),wxAccessToken.getExpiresIn(), TimeUnit.SECONDS);
            }
        }catch (Exception e){
            log.error("微信配置初始化失败");
        }
    }

    /**
     * 定时刷新微信服务acckeytoken
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshToken() {
        RLock lock = redissonClient.getLock(this.Acckey);
        try {
            if (lock.tryLock(5,30, TimeUnit.SECONDS)) {
                if (redisService.hasKey(Tokenkey) && redisService.getExpire(Tokenkey) > this.expireTime) {
                    log.info("刷新token不需要");
                    this.access_key = redisService.getCacheObject(Tokenkey);
                    return;
                }
                log.info("定时刷新微信服务acckeytoken开始");
                String AccessToken = remoteWxService.getWxAccessToken(this.AcckeyGrantType, this.appid, this.secret);
                WxAccessToken wxAccessToken = JSONObject.parseObject(AccessToken, WxAccessToken.class);
                this.access_key = wxAccessToken.getAccessToken();
                redisService.setCacheObject(Tokenkey, wxAccessToken.getAccessToken(),wxAccessToken.getExpiresIn(), TimeUnit.SECONDS);
            }
        }catch (Exception e){
            log.error("定时刷新微信服务acckeytoken开始失败");
        }
    }
}
