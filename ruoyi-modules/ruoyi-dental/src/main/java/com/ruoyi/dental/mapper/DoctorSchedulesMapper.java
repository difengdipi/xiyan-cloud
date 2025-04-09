package com.ruoyi.dental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.vo.DoctorSchedulesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医生行程Mapper接口
 *
 * @author zh
 * @date 2025-04-03
 */
@Mapper
public interface DoctorSchedulesMapper extends BaseMapper<DoctorSchedules> {


        /**
         * 查询医生行程
         *
         * @param id 医生行程主键
         * @return 医生行程
         */
        public DoctorSchedulesVo selectDoctorSchedulesById(Long id);

        /**
         * 查询医生行程列表
         *
         * @param doctorSchedules 医生行程
         * @return 医生行程集合
         */
        public List<DoctorSchedulesVo> selectDoctorSchedulesList(DoctorSchedulesVo doctorSchedules);

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
        public int updateDoctorSchedules(DoctorSchedulesVo doctorSchedules);

        /**
         * 删除医生行程
         *
         * @param id 医生行程主键
         * @return 结果
         */
        public int deleteDoctorSchedulesById(Long id);

        /**
         * 批量删除医生行程
         *
         * @param ids 需要删除的数据主键集合
         * @return 结果
         */
        public int deleteDoctorSchedulesByIds(Long[] ids);
    }
