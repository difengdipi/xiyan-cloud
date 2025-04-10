package com.ruoyi.dental.Schedules;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import com.ruoyi.dental.service.IDoctorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 统计一个医生的预约量
 * @author: zh
 * @Create : 2025/4/10
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Component
@Slf4j
public class DoctorsSchedule {

    @Autowired
    private IDoctorSchedulesService doctorSchedulesService;
    @Autowired
    private IDoctorsService doctorsService;

    @Scheduled(cron = "0/60 * * * * ?")
    public void countDoctorAppNum(){
        List<Doctors> list = doctorsService.list();
        for (Doctors doctors : list){
            //根据医生的id 获取行程预约量
            List<DoctorSchedules> list1 = doctorSchedulesService.list(new LambdaQueryWrapper<DoctorSchedules>()
                    .eq(DoctorSchedules::getDoctorId, doctors.getId())
            );
            Integer appNum = list1.stream().map(DoctorSchedules::getAppNum).reduce(Integer::sum).get();
            Long num =0L;
            doctorsService.update(new LambdaUpdateWrapper<Doctors>()
                    .eq(Doctors::getId, doctors.getId())
                    .set(Doctors::getAppNum, num+appNum)
            );
        }
    }
}
