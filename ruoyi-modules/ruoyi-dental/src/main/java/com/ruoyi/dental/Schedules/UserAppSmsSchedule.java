package com.ruoyi.dental.Schedules;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.dental.domain.vo.UserAppInfoDto;
import com.ruoyi.dental.service.IUserAppInfoService;
import com.ruoyi.system.api.SmsApi.RemoteSmsService;
import com.ruoyi.system.api.constants.SmsRequest;
import com.ruoyi.system.api.constants.SmsTemplateCode;
import com.ruoyi.system.api.constants.SmsType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description: 用户就诊短信提醒类
 * @author: zh
 * @Create : 2025/4/30
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class UserAppSmsSchedule {

    @Autowired
    RemoteSmsService remoteSmsService;

    @Autowired
    IUserAppInfoService iUserAppInfoService;

    /**
     *
     * 定时任务每天想手机发送消息并且时间不超过18点
     */
    @Scheduled(cron = "0/300 * * * * ?")
    public void sendUserApp(){
        UserAppInfoDto userAppInfoDto = new UserAppInfoDto();
        //设置查询的状态
        userAppInfoDto.setStatus((short)0);
        List<UserAppInfoDto> userAppInfoDtos = iUserAppInfoService.selectUserAppInfoList(userAppInfoDto);
        for(UserAppInfoDto userAppInfoDto1 : userAppInfoDtos){
            //保留下预约日期是在当前日期七点到下午5点之间的预约
//            if(userAppInfoDto1.getScheduleDate().getTime()>System.currentTimeMillis()&&userAppInfoDto1.getScheduleDate().getTime()<System.currentTimeMillis()+1000*60*60*24*7){
                //发送短信
                Map<String, Object> param = new HashMap<>();
//               TODO 时间提醒的模版: 这里应该是时间的模版参数
                param.put(SmsTemplateCode.SMS_CODE, ThreadLocalRandom.current().nextInt(100000, 1000000));
                log.info("发送短信:{}", userAppInfoDto1.getPhone());
                CompletableFuture.supplyAsync(()->{
                    R r = remoteSmsService.sendUserApp(userAppInfoDto1.getPhone(), SmsRequest.builder().type(SmsType.ALIYUM_SMS).param(param).build(), SecurityConstants.INNER);
                    return r;
                }).whenComplete((r,e)->{
                    if(e!=null){
                        //这里应该设置一个补偿消息，并且将日志保存
                        log.info("发送短信失败:{}", e.getMessage());
                    }
                    if(r.getCode()!=200){
                        System.out.println("发送短信失败:"+r.getMsg());
                    }
                });
//            }
            return ;
        }

    }
}
