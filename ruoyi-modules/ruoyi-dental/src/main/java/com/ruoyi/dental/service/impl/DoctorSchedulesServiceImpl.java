package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.DoctorSchedulesVo;
import com.ruoyi.dental.mapper.DoctorSchedulesMapper;
import com.ruoyi.dental.mapper.UserAppInfoMapper;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 医生行程Service业务层处理
 * 
 * @author zh
 * @date 2025-04-03
 */
@Service
@Slf4j
public class DoctorSchedulesServiceImpl extends ServiceImpl<DoctorSchedulesMapper,DoctorSchedules> implements IDoctorSchedulesService
{
    @Autowired
    private DoctorSchedulesMapper doctorSchedulesMapper;

    /**
     * 查询医生行程
     * 
     * @param id 医生行程主键
     * @return 医生行程
     */
    @Override
    public DoctorSchedulesVo selectDoctorSchedulesById(Long id)
    {
        return doctorSchedulesMapper.selectDoctorSchedulesById(id);
    }

    /**
     * 查询医生行程列表
     * 
     * @param doctorSchedules 医生行程
     * @return 医生行程
     */
    @Override
    public List<DoctorSchedulesVo> selectDoctorSchedulesList(DoctorSchedulesVo doctorSchedules)
    {
        return doctorSchedulesMapper.selectDoctorSchedulesList(doctorSchedules);
    }

    /**
     * 新增医生行程
     * 
     * @param doctorSchedules 医生行程
     * @return 结果
     */
    @Override
    public int insertDoctorSchedules(DoctorSchedules doctorSchedules)
    {
        doctorSchedules.setCreateTime(LocalDateTime.now());
        return doctorSchedulesMapper.insertDoctorSchedules(doctorSchedules);
    }

    /**
     * 修改医生行程
     * 
     * @param doctorSchedules 医生行程
     * @return 结果
     */
    @Override
    public int updateDoctorSchedules(DoctorSchedulesVo doctorSchedules)
    {
        doctorSchedules.setUpdateTime(LocalDateTime.now());
        return doctorSchedulesMapper.updateDoctorSchedules(doctorSchedules);
    }

    /**
     * 批量删除医生行程
     *
     * @param ids    需要删除的医生行程主键
     * @param reason
     * @return 结果
     */
    @Autowired
    UserAppInfoMapper userAppInfoMapper;
    @Override
    public int deleteDoctorSchedulesByIds(Long[] ids, String reason)
    {
        //变成更新医生的状态保存全部的原因
        LambdaUpdateWrapper<DoctorSchedules> doctorSchedulesLambdaUpdateWrapper = new LambdaUpdateWrapper<DoctorSchedules>()
                .in(DoctorSchedules::getId,ids)
                .set(DoctorSchedules::getStatus,0);
        //取消对应的用户预约
        CompletableFuture.supplyAsync(()->{
            int update = userAppInfoMapper.update(new LambdaUpdateWrapper<UserAppInfo>()
                    .in(UserAppInfo::getScheduleId, ids)
                    .set(UserAppInfo::getStatus, 2)
                    .set(UserAppInfo::getCancelReason, reason)
            );
            return update;
        }).whenCompleteAsync((a,b)-> {
            if(a <= 0){
                log.info("取消对应的用户预约失败,对应的行程id为:{}",ids);
            }
            if(b!=null){
                log.info("取消对应的用户预约失败,对应的行程id为:{}",ids);
                throw new RuntimeException(b);
            }
        });
        boolean update = update(doctorSchedulesLambdaUpdateWrapper);
        return update?1:0;
    }

    /**
     * 删除医生行程信息
     * 
     * @param id 医生行程主键
     * @return 结果
     */
    @Override
    public int deleteDoctorSchedulesById(Long id)
    {
        return doctorSchedulesMapper.deleteDoctorSchedulesById(id);
    }

    @Override
    public int updateByScheduleId(Long scheduleId) {
        return doctorSchedulesMapper.updateByScheduleId(scheduleId);
    }
}
