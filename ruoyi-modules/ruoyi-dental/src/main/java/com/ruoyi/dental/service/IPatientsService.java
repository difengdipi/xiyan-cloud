package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dental.domain.Patients;

import java.util.List;

/**
 * 患者列表Service接口
 * 
 * @author zh
 * @date 2025-03-15
 */
public interface IPatientsService extends IService<Patients>
{
    /**
     * 查询患者列表
     * 
     * @param patientId 患者列表主键
     * @return 患者列表
     */
    public Patients selectPatientsByPatientId(Long patientId);

    /**
     * 查询患者列表列表
     * 
     * @param patients 患者列表
     * @return 患者列表集合
     */
    public List<Patients> selectPatientsList(Patients patients);

    /**
     * 新增患者列表
     * 
     * @param patients 患者列表
     * @return 结果
     */
    public int insertPatients(Patients patients);

    /**
     * 修改患者列表
     * 
     * @param patients 患者列表
     * @return 结果
     */
    public int updatePatients(Patients patients);

    /**
     * 批量删除患者列表
     * 
     * @param patientIds 需要删除的患者列表主键集合
     * @return 结果
     */
    public int deletePatientsByPatientIds(Long[] patientIds);

    /**
     * 删除患者列表信息
     * 
     * @param patientId 患者列表主键
     * @return 结果
     */
    public int deletePatientsByPatientId(Long patientId);
}
