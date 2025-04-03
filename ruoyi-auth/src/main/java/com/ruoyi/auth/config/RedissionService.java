package com.ruoyi.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@ConfigurationProperties
@Slf4j
public class RedissionService {
    @Value("${spring.redis.host}")
    public String host;
    @Value("${spring.redis.port}")
    public Integer port;
    @Value("${spring.redis.password}")
    public String password;
    @Value("${spring.redis.timeout}")
    public Integer  timeout = 1000;
    @Bean
    public RedissonClient getRedissonClient() {
        log.info("RedissonClient初始化开始:{}，port:{},password:{}",this.host,this.port,this.password);
        Config config = new Config();
        if(StringUtils.isEmpty(password)){
            config.useSingleServer().setAddress("redis://"+host+":"+port);
        }else{
            config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password).setTimeout(timeout);
        }
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
