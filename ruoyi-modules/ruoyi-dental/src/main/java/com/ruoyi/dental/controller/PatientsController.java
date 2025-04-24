package com.ruoyi.dental.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.Patients;
import com.ruoyi.dental.service.IPatientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 患者列表Controller
 * 
 * @author zh
 * @date 2025-03-15
 */
@RestController
@RequestMapping("/patients")
@Tag(name = "患者列表")
public class PatientsController extends BaseController
{
    @Autowired
    private IPatientsService patientsService;

    /**
     * 查询患者列表列表
     */
    @RequiresPermissions("dental:patients:list")
    @GetMapping("/list")
    @Operation(summary = "查询患者列表列表")
    public TableDataInfo list(Patients patients)
    {
        startPage();
        List<Patients> list = patientsService.selectPatientsList(patients);
        return getDataTable(list);
    }

    /**
     * 导出患者列表列表
     */
    @RequiresPermissions("dental:patients:export")
    @Operation(summary = "导出患者列表列表")
    @Log(title = "患者列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Patients patients)
    {
        List<Patients> list = patientsService.selectPatientsList(patients);
        ExcelUtil<Patients> util = new ExcelUtil<Patients>(Patients.class);
        util.exportExcel(response, list, "患者列表数据");
    }

    /**
     * 获取患者列表详细信息
     */
    @RequiresPermissions("dental:patients:query")
    @GetMapping(value = "/{patientId}")
    @Operation(summary = "获取患者列表详细信息")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        return success(patientsService.selectPatientsByPatientId(patientId));
    }

    /**
     * 新增患者列表
     */
    @RequiresPermissions("dental:patients:add")
    @Operation(summary = "新增患者列表")
    @Log(title = "患者列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patients patients)
    {
        return toAjax(patientsService.insertPatients(patients));
    }

    /**
     * 修改患者列表
     */
    @RequiresPermissions("dental:patients:edit")
    @Log(title = "患者列表", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改患者列表")
    public AjaxResult edit(@RequestBody Patients patients)
    {
        return toAjax(patientsService.updatePatients(patients));
    }

    /**
     * 删除患者列表
     */
    @RequiresPermissions("dental:patients:remove")
    @Log(title = "患者列表", businessType = BusinessType.DELETE)
    @Operation(summary = "删除患者列表")
	@DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(patientsService.deletePatientsByPatientIds(patientIds));
    }
}
