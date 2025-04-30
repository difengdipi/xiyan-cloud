package com.ruoyi.sms.handler;

import com.ruoyi.sms.inter.SmsComInterFace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class SmsTypeFactory {
    // 短信方式常量
    private static Map<String, SmsComInterFace> map = new ConcurrentHashMap<>();
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){

        if(map.isEmpty()){
            Map<String, SmsComInterFace> beansOfType = applicationContext.getBeansOfType(SmsComInterFace.class);
            beansOfType.forEach((k,v)->{
                map.put(k,v);
            });
        }
    }


    /**
     * 短信方式
     * @param type 传入短信方式
     * @return
     */
    public static SmsComInterFace getSms(String type) {
        SmsComInterFace sms = null;
        if(map.containsKey(type)){
            sms = map.get(type);
        }
        if (sms == null) {
            throw new NullPointerException("方式选择错误");
        }
        return sms;
    }
}
