package com.ruoyi.dental.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医生行程Controller
 * 
 * @author zh
 * @date 2025-04-03
 */
@RestController
@RequestMapping("/schedules")
@Tag(name = "医生行程安排")
public class DoctorSchedulesController extends BaseController
{
    @Autowired
    private IDoctorSchedulesService doctorSchedulesService;

    /**
     * 查询医生行程列表
     */
    @RequiresPermissions("dental:schedules:list")
    @GetMapping("/list")
    @Operation(summary = "医生行程列表")
    public TableDataInfo list(DoctorSchedules doctorSchedules)
    {
        startPage();
        List<DoctorSchedules> list = doctorSchedulesService.selectDoctorSchedulesList(doctorSchedules);
        return getDataTable(list);
    }




    /**
     * 导出医生行程列表
     */
    @RequiresPermissions("dental:schedules:export")
    @Log(title = "医生行程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出医生行程列表")
    public void export(HttpServletResponse response, DoctorSchedules doctorSchedules)
    {
        List<DoctorSchedules> list = doctorSchedulesService.selectDoctorSchedulesList(doctorSchedules);
        ExcelUtil<DoctorSchedules> util = new ExcelUtil<DoctorSchedules>(DoctorSchedules.class);
        util.exportExcel(response, list, "医生行程数据");
    }

    /**
     * 获取医生行程详细信息
     */
    @RequiresPermissions("dental:schedules:query")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取医生行程详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(doctorSchedulesService.selectDoctorSchedulesById(id));
    }

    /**
     * 新增医生行程
     */
    @RequiresPermissions("dental:schedules:add")
    @Log(title = "医生行程", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "新增医生行程")

    public AjaxResult add(@RequestBody DoctorSchedules doctorSchedules)
    {
        return toAjax(doctorSchedulesService.save(doctorSchedules));
    }

    /**
     * 修改医生行程
     */
    @RequiresPermissions("dental:schedules:edit")
    @Log(title = "医生行程", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改医生行程")

    public AjaxResult edit(@RequestBody DoctorSchedules doctorSchedules)
    {
        return toAjax(doctorSchedulesService.updateDoctorSchedules(doctorSchedules));
    }

    /**
     * 删除医生行程
     */
    @RequiresPermissions("dental:schedules:remove")
    @Log(title = "医生行程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @Operation(summary = "删除医生行程")

    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(doctorSchedulesService.deleteDoctorSchedulesByIds(ids));
    }
}
