package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dental.domain.Appointments;
import com.ruoyi.dental.mapper.AppointmentsMapper;
import com.ruoyi.dental.service.IAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 挂号建档Service业务层处理
 * 
 * @author zh
 * @date 2025-03-15
 */
@Service
public class AppointmentsServiceImpl  extends ServiceImpl<AppointmentsMapper, Appointments> implements IAppointmentsService
{
    @Autowired
    private AppointmentsMapper appointmentsMapper;

    /**
     * 查询挂号建档
     * 
     * @param appointmentId 挂号建档主键
     * @return 挂号建档
     */
    @Override
    public Appointments selectAppointmentsByAppointmentId(Long appointmentId)
    {
        return appointmentsMapper.selectAppointmentsByAppointmentId(appointmentId);
    }

    /**
     * 查询挂号建档列表
     * 
     * @param appointments 挂号建档
     * @return 挂号建档
     */
    @Override
    public List<Appointments> selectAppointmentsList(Appointments appointments)
    {
        return appointmentsMapper.selectAppointmentsList(appointments);
    }

    /**
     * 新增挂号建档
     * 
     * @param appointments 挂号建档
     * @return 结果
     */
    @Override
    public int insertAppointments(Appointments appointments)
    {
        return appointmentsMapper.insertAppointments(appointments);
    }

    /**
     * 修改挂号建档
     * 
     * @param appointments 挂号建档
     * @return 结果
     */
    @Override
    public int updateAppointments(Appointments appointments)
    {
        return appointmentsMapper.updateAppointments(appointments);
    }

    /**
     * 批量删除挂号建档
     * 
     * @param appointmentIds 需要删除的挂号建档主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentsByAppointmentIds(Long[] appointmentIds)
    {
        return appointmentsMapper.deleteAppointmentsByAppointmentIds(appointmentIds);
    }

    /**
     * 删除挂号建档信息
     * 
     * @param appointmentId 挂号建档主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentsByAppointmentId(Long appointmentId)
    {
        return appointmentsMapper.deleteAppointmentsByAppointmentId(appointmentId);
    }
}
