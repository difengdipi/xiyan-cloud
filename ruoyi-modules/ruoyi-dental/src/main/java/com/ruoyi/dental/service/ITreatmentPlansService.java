package com.ruoyi.dental.service;

import com.ruoyi.dental.domain.TreatmentPlans;
import com.ruoyi.dental.domain.vo.TreatmentPlansVo;

import java.util.List;

/**
 * 治疗计划Service接口
 * 
 * @author zh
 * @date 2025-04-10
 */
public interface ITreatmentPlansService 
{
    /**
     * 查询治疗计划
     * 
     * @param planId 治疗计划主键
     * @return 治疗计划
     */
    public TreatmentPlans selectTreatmentPlansByPlanId(Long planId);

    /**
     * 查询治疗计划列表
     * 
     * @param treatmentPlans 治疗计划
     * @return 治疗计划集合
     */
    public List<TreatmentPlansVo> selectTreatmentPlansList(TreatmentPlansVo treatmentPlans);

    /**
     * 新增治疗计划
     * 
     * @param treatmentPlans 治疗计划
     * @return 结果
     */
    public int insertTreatmentPlans(TreatmentPlans treatmentPlans);

    /**
     * 修改治疗计划
     * 
     * @param treatmentPlans 治疗计划
     * @return 结果
     */
    public int updateTreatmentPlans(TreatmentPlans treatmentPlans);

    /**
     * 批量删除治疗计划
     * 
     * @param planIds 需要删除的治疗计划主键集合
     * @return 结果
     */
    public int deleteTreatmentPlansByPlanIds(Long[] planIds);

    /**
     * 删除治疗计划信息
     * 
     * @param planId 治疗计划主键
     * @return 结果
     */
    public int deleteTreatmentPlansByPlanId(Long planId);
}
