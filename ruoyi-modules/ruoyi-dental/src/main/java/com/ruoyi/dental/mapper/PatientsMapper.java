package com.ruoyi.dental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.dental.domain.Patients;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 患者列表Mapper接口
 * 
 * @author zh
 * @date 2025-03-15
 */
@Mapper
public interface PatientsMapper  extends BaseMapper<Patients>
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
     * 删除患者列表
     * 
     * @param patientId 患者列表主键
     * @return 结果
     */
    public int deletePatientsByPatientId(Long patientId);

    /**
     * 批量删除患者列表
     * 
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientsByPatientIds(Long[] patientIds);
}
