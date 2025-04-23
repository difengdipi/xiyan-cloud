package com.ruoyi.dental.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.dental.domain.TreatmentPlans;
import com.ruoyi.dental.domain.vo.TreatmentPlansVo;
import com.ruoyi.dental.service.ITreatmentPlansService;
import com.ruoyi.system.api.domain.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 治疗计划Controller
 * 
 * @author zh
 * @date 2025-04-10
 */
@RestController
@RequestMapping("/plans")
@Tag(name ="治疗计划")
public class TreatmentPlansController extends BaseController
{
    @Autowired
    private ITreatmentPlansService treatmentPlansService;
    @Autowired
    TokenService tokenService;

    /**
     * 查询治疗计划列表
     */
    @RequiresPermissions("dental:plans:list")
    @GetMapping("/list")
    public TableDataInfo list(TreatmentPlansVo treatmentPlans)
    {
        startPage();
        List<TreatmentPlansVo> list = treatmentPlansService.selectTreatmentPlansList(treatmentPlans);
        return getDataTable(list);
    }

    /**
     * 导出治疗计划列表
     */
    @RequiresPermissions("dental:plans:export")
    @Log(title = "治疗计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TreatmentPlansVo treatmentPlans)
    {
        List<TreatmentPlansVo> list = treatmentPlansService.selectTreatmentPlansList(treatmentPlans);
        ExcelUtil<TreatmentPlansVo> util = new ExcelUtil<>(TreatmentPlansVo.class);
        util.exportExcel(response, list, "治疗计划数据");
    }

    /**
     * 获取治疗计划详细信息
     */
    @RequiresPermissions("dental:plans:query")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(treatmentPlansService.selectTreatmentPlansByPlanId(planId));
    }

    /**
     * 新增治疗计划
     */
    @RequiresPermissions("dental:plans:add")
    @Log(title = "治疗计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TreatmentPlans treatmentPlans)
    {
        treatmentPlans.setCreatedTime(new Date());
        return toAjax(treatmentPlansService.insertTreatmentPlans(treatmentPlans));
    }

    /**
     * 修改治疗计划
     */
    @RequiresPermissions("dental:plans:edit")
    @Log(title = "治疗计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TreatmentPlans treatmentPlans)
    {
        SysUser sysUser = tokenService.getLoginUser().getSysUser();
        treatmentPlans.setDoctorId(sysUser.getUserId());
        treatmentPlans.setUpdatedTime(new Date());
        return toAjax(treatmentPlansService.updateTreatmentPlans(treatmentPlans));
    }

    /**
     * 删除治疗计划
     */
    @RequiresPermissions("dental:plans:remove")
    @Log(title = "治疗计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(treatmentPlansService.deleteTreatmentPlansByPlanIds(planIds));
    }
    @GetMapping("/app/{id}")
    @Operation(summary ="根据预约id获取预约结果")
    public R getData(@PathVariable("id")Long id){
        //根据预约id获取预约结果
        TreatmentPlansVo treatmentPlans = new TreatmentPlansVo();
        treatmentPlans.setUserAppId(id);
        List<TreatmentPlansVo> list = treatmentPlansService.selectTreatmentPlansList(treatmentPlans);
        return R.ok(list);
    }
}
