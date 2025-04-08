package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.mapper.DoctorSchedulesMapper;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 医生行程Service业务层处理
 * 
 * @author zh
 * @date 2025-04-03
 */
@Service
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
    public DoctorSchedules selectDoctorSchedulesById(Long id)
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
    public List<DoctorSchedules> selectDoctorSchedulesList(DoctorSchedules doctorSchedules)
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
    public int updateDoctorSchedules(DoctorSchedules doctorSchedules)
    {
        doctorSchedules.setUpdateTime(LocalDateTime.now());
        return doctorSchedulesMapper.updateDoctorSchedules(doctorSchedules);
    }

    /**
     * 批量删除医生行程
     * 
     * @param ids 需要删除的医生行程主键
     * @return 结果
     */
    @Override
    public int deleteDoctorSchedulesByIds(Long[] ids)
    {
        return doctorSchedulesMapper.deleteDoctorSchedulesByIds(ids);
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
}
