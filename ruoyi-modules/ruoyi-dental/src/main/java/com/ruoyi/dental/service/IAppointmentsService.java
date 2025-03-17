package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dental.domain.Appointments;

import java.util.List;

/**
 * 挂号建档Service接口
 * 
 * @author zh
 * @date 2025-03-15
 */
public interface IAppointmentsService  extends IService<Appointments>
{
    /**
     * 查询挂号建档
     * 
     * @param appointmentId 挂号建档主键
     * @return 挂号建档
     */
    public Appointments selectAppointmentsByAppointmentId(Long appointmentId);

    /**
     * 查询挂号建档列表
     * 
     * @param appointments 挂号建档
     * @return 挂号建档集合
     */
    public List<Appointments> selectAppointmentsList(Appointments appointments);

    /**
     * 新增挂号建档
     * 
     * @param appointments 挂号建档
     * @return 结果
     */
    public int insertAppointments(Appointments appointments);

    /**
     * 修改挂号建档
     * 
     * @param appointments 挂号建档
     * @return 结果
     */
    public int updateAppointments(Appointments appointments);

    /**
     * 批量删除挂号建档
     * 
     * @param appointmentIds 需要删除的挂号建档主键集合
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentIds(Long[] appointmentIds);

    /**
     * 删除挂号建档信息
     * 
     * @param appointmentId 挂号建档主键
     * @return 结果
     */
    public int deleteAppointmentsByAppointmentId(Long appointmentId);
}
