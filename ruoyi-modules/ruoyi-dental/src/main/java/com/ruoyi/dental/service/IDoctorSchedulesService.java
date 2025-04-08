package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dental.domain.DoctorSchedules;

import java.util.List;

/**
 * 医生行程Service接口
 * 
 * @author zh
 * @date 2025-04-03
 */
public interface IDoctorSchedulesService  extends IService<DoctorSchedules>
{
    /**
     * 查询医生行程
     * 
     * @param id 医生行程主键
     * @return 医生行程
     */
    public DoctorSchedules selectDoctorSchedulesById(Long id);

    /**
     * 查询医生行程列表
     * 
     * @param doctorSchedules 医生行程
     * @return 医生行程集合
     */
    public List<DoctorSchedules> selectDoctorSchedulesList(DoctorSchedules doctorSchedules);

    /**
     * 新增医生行程
     * 
     * @param doctorSchedules 医生行程
     * @return 结果
     */
    public int insertDoctorSchedules(DoctorSchedules doctorSchedules);

    /**
     * 修改医生行程
     * 
     * @param doctorSchedules 医生行程
     * @return 结果
     */
    public int updateDoctorSchedules(DoctorSchedules doctorSchedules);

    /**
     * 批量删除医生行程
     * 
     * @param ids 需要删除的医生行程主键集合
     * @return 结果
     */
    public int deleteDoctorSchedulesByIds(Long[] ids);

    /**
     * 删除医生行程信息
     * 
     * @param id 医生行程主键
     * @return 结果
     */
    public int deleteDoctorSchedulesById(Long id);
}
