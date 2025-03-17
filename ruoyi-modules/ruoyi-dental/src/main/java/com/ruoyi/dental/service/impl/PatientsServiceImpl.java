package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dental.domain.Patients;
import com.ruoyi.dental.mapper.PatientsMapper;
import com.ruoyi.dental.service.IPatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 患者列表Service业务层处理
 * 
 * @author zh
 * @date 2025-03-15
 */
@Service
public class PatientsServiceImpl extends ServiceImpl<PatientsMapper,Patients> implements IPatientsService
{
    @Autowired
    private PatientsMapper patientsMapper;

    /**
     * 查询患者列表
     * 
     * @param patientId 患者列表主键
     * @return 患者列表
     */
    @Override
    public Patients selectPatientsByPatientId(Long patientId)
    {
        return patientsMapper.selectPatientsByPatientId(patientId);
    }

    /**
     * 查询患者列表列表
     * 
     * @param patients 患者列表
     * @return 患者列表
     */
    @Override
    public List<Patients> selectPatientsList(Patients patients)
    {
        return patientsMapper.selectPatientsList(patients);
    }

    /**
     * 新增患者列表
     * 
     * @param patients 患者列表
     * @return 结果
     */
    @Override
    public int insertPatients(Patients patients)
    {
        return patientsMapper.insertPatients(patients);
    }

    /**
     * 修改患者列表
     * 
     * @param patients 患者列表
     * @return 结果
     */
    @Override
    public int updatePatients(Patients patients)
    {
        return patientsMapper.updatePatients(patients);
    }

    /**
     * 批量删除患者列表
     * 
     * @param patientIds 需要删除的患者列表主键
     * @return 结果
     */
    @Override
    public int deletePatientsByPatientIds(Long[] patientIds)
    {
        return patientsMapper.deletePatientsByPatientIds(patientIds);
    }

    /**
     * 删除患者列表信息
     * 
     * @param patientId 患者列表主键
     * @return 结果
     */
    @Override
    public int deletePatientsByPatientId(Long patientId)
    {
        return patientsMapper.deletePatientsByPatientId(patientId);
    }
}
