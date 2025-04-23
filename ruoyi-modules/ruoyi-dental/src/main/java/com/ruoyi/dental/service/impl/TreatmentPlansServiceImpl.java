package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.datascope.annotation.DataScope;
import com.ruoyi.dental.domain.TreatmentPlans;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.TreatmentPlansVo;
import com.ruoyi.dental.mapper.TreatmentPlansMapper;
import com.ruoyi.dental.service.ITreatmentPlansService;
import com.ruoyi.dental.service.IUserAppInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 治疗计划Service业务层处理
 * 
 * @author zh
 * @date 2025-04-10
 */
@Service
@Slf4j
public class TreatmentPlansServiceImpl implements ITreatmentPlansService 
{
    @Autowired
    private TreatmentPlansMapper treatmentPlansMapper;
    @Autowired
    IUserAppInfoService userAppInfoService;


    /**
     * 查询治疗计划
     * 
     * @param planId 治疗计划主键
     * @return 治疗计划
     */
    @Override
    public TreatmentPlans selectTreatmentPlansByPlanId(Long planId)
    {
        return treatmentPlansMapper.selectTreatmentPlansByPlanId(planId);
    }

    /**
     * 查询治疗计划列表
     * 
     * @param treatmentPlans 治疗计划
     * @return 治疗计划
     */
    @Override
    @DataScope(deptAlias = "tbd", userAlias = "tbd")
    public List<TreatmentPlansVo> selectTreatmentPlansList(TreatmentPlansVo treatmentPlans)
    {
        return treatmentPlansMapper.selectTreatmentPlansList(treatmentPlans);
    }

    /**
     * 新增治疗计划
     * 
     * @param treatmentPlans 治疗计划
     * @return 结果
     */

    @Override
    public int insertTreatmentPlans(TreatmentPlans treatmentPlans)
    {
        //获取医生id
        UserAppInfo byId = userAppInfoService.getById(treatmentPlans.getUserAppId());
        treatmentPlans.setCreatedTime(new Date());
        treatmentPlans.setDoctorId(byId.getDoctorId());
        //新增完治疗计划将用户前端状态码改为:已完成
        CompletableFuture.runAsync(()->{
            LambdaUpdateWrapper<UserAppInfo> eq = new LambdaUpdateWrapper<UserAppInfo>()
                    .eq(UserAppInfo::getId, treatmentPlans.getUserAppId())
                            .eq(UserAppInfo::getStatus, 3)
                                    .set(UserAppInfo::getStatus, 1);
            userAppInfoService.update(eq);
        }).whenCompleteAsync((a,b)->{
            if(b!=null){
              log.info("异常:{}",b.getMessage());
            }
            if(a!=null){
                log.info("成功");
            }
        });

        return treatmentPlansMapper.insertTreatmentPlans(treatmentPlans);
    }

    /**
     * 修改治疗计划
     * 
     * @param treatmentPlans 治疗计划
     * @return 结果
     */
    @Override
    public int updateTreatmentPlans(TreatmentPlans treatmentPlans)
    {
        treatmentPlans.setUpdatedTime(new Date());
        CompletableFuture.runAsync(()->{
            userAppInfoService.update(new LambdaUpdateWrapper<UserAppInfo>()
                    .eq(UserAppInfo::getId, treatmentPlans.getUserAppId())
                    .set(UserAppInfo::getStatus, 1)
            );
        }).whenComplete((a,b)->{
            if(b!=null){
                log.info("异常:{}",b.getMessage());
            }
            if(a!=null){
                log.info("成功");
            }
        });
        return treatmentPlansMapper.updateTreatmentPlans(treatmentPlans);
    }

    /**
     * 批量删除治疗计划
     * 
     * @param planIds 需要删除的治疗计划主键
     * @return 结果
     */
    @Override
    public int deleteTreatmentPlansByPlanIds(Long[] planIds)
    {
        return treatmentPlansMapper.deleteTreatmentPlansByPlanIds(planIds);
    }

    /**
     * 删除治疗计划信息
     * 
     * @param planId 治疗计划主键
     * @return 结果
     */
    @Override
    public int deleteTreatmentPlansByPlanId(Long planId)
    {
        return treatmentPlansMapper.deleteTreatmentPlansByPlanId(planId);
    }
}
