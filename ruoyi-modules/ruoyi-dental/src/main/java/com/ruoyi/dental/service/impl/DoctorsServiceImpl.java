package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.dental.mapper.DoctorsMapper;
import com.ruoyi.dental.service.IDoctorsService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 医生信息Service业务层处理
 * 
 * @author zh
 * @date 2025-03-15
 */
@Service
public class DoctorsServiceImpl extends ServiceImpl<DoctorsMapper,Doctors> implements IDoctorsService
{
    @Autowired
    private DoctorsMapper doctorsMapper;

    /**
     * 查询医生信息
     * 
     * @param id 医生信息主键
     * @return 医生信息
     */
    @Override
    public Doctors selectDoctorsById(Long id)
    {
        return doctorsMapper.selectDoctorsById(id);
    }

    /**
     * 查询医生信息列表
     * 
     * @param doctors 医生信息
     * @return 医生信息
     */
    @Override
    public List<Doctors> selectDoctorsList(Doctors doctors)
    {
        return doctorsMapper.selectDoctorsList(doctors);
    }

    /**
     * 新增医生信息
     * 
     * @param doctors 医生信息
     * @return 结果
     */
    @Override
    public int insertDoctors(Doctors doctors)
    {
        return doctorsMapper.insertDoctors(doctors);
    }

    /**
     * 修改医生信息
     * 
     * @param doctors 医生信息
     * @return 结果
     */
    @Override
    public int updateDoctors(Doctors doctors)
    {
        return doctorsMapper.updateDoctors(doctors);
    }

    /**
     * 批量删除医生信息
     * 
     * @param ids 需要删除的医生信息主键
     * @return 结果
     */
    @Override
    public int deleteDoctorsByIds(Long[] ids)
    {
        return doctorsMapper.deleteDoctorsByIds(ids);
    }

    /**
     * 删除医生信息信息
     * 
     * @param id 医生信息主键
     * @return 结果
     */
    @Override
    public int deleteDoctorsById(Long id)
    {
        return doctorsMapper.deleteDoctorsById(id);
    }

    /**
     * 根据用户信息创建医生信息
     * @param sysUser
     * @return
     */
    public R insertBySysUser(SysUser sysUser) {
        if(sysUser.getUserId() == null) {
            return R.fail("用户ID为空");
        }
        if(StringUtils.isEmpty(sysUser.getUserName())){
            return R.fail("用户名为空");
        }
        System.out.println("sysUser::::"+sysUser);
        Set<Long> collect = Arrays.stream(sysUser.getRoleIds()).filter(roleId -> roleId == 4).collect(Collectors.toSet());
        //角色确定权限------如果是医生角色就创建否则就不创建-并给出提示就ok
        if(collect.isEmpty()){
            return R.fail("不是医生角色，无法创建医生信息");
        }
        Doctors doctors = new Doctors();
        doctors.setUserId(sysUser.getUserId());
        doctors.setName(sysUser.getNickName());
        doctors.setStatus(0L);
        doctors.setCreateTime(LocalDateTime.now());
        return doctorsMapper.insert(doctors) > 1 ? R.ok() : R.fail("创建医生信息失败");
    }
}
