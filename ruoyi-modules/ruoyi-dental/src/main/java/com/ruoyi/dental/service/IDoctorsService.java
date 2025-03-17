package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.system.api.domain.SysUser;

import java.util.List;

/**
 * 医生信息Service接口
 * 
 * @author zh
 * @date 2025-03-15
 */
public interface IDoctorsService  extends IService<Doctors>
{
    /**
     * 查询医生信息
     * 
     * @param id 医生信息主键
     * @return 医生信息
     */
    public Doctors selectDoctorsById(Long id);

    /**
     * 查询医生信息列表
     * 
     * @param doctors 医生信息
     * @return 医生信息集合
     */
    public List<Doctors> selectDoctorsList(Doctors doctors);

    /**
     * 新增医生信息
     * 
     * @param doctors 医生信息
     * @return 结果
     */
    public int insertDoctors(Doctors doctors);

    /**
     * 修改医生信息
     * 
     * @param doctors 医生信息
     * @return 结果
     */
    public int updateDoctors(Doctors doctors);

    /**
     * 批量删除医生信息
     * 
     * @param ids 需要删除的医生信息主键集合
     * @return 结果
     */
    public int deleteDoctorsByIds(Long[] ids);

    /**
     * 删除医生信息信息
     * 
     * @param id 医生信息主键
     * @return 结果
     */
    public int deleteDoctorsById(Long id);

    R insertBySysUser(SysUser sysUser);
}
