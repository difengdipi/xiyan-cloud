package com.ruoyi.dental.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.Appointments;
import com.ruoyi.dental.service.IAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 挂号建档Controller
 * 
 * @author zh
 * @date 2025-03-15
 */
@RestController
@RequestMapping("/appointments")
public class AppointmentsController extends BaseController
{
    @Autowired
    private IAppointmentsService appointmentsService;

    /**
     * 查询挂号建档列表
     */
    @RequiresPermissions("dental:appointments:list")
    @GetMapping("/list")
    public TableDataInfo list(Appointments appointments)
    {
        startPage();
        List<Appointments> list = appointmentsService.selectAppointmentsList(appointments);
        return getDataTable(list);
    }

    /**
     * 导出挂号建档列表
     */
    @RequiresPermissions("dental:appointments:export")
    @Log(title = "挂号建档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Appointments appointments)
    {
        List<Appointments> list = appointmentsService.selectAppointmentsList(appointments);
        ExcelUtil<Appointments> util = new ExcelUtil<Appointments>(Appointments.class);
        util.exportExcel(response, list, "挂号建档数据");
    }

    /**
     * 获取挂号建档详细信息
     */
    @RequiresPermissions("dental:appointments:query")
    @GetMapping(value = "/{appointmentId}")
    public AjaxResult getInfo(@PathVariable("appointmentId") Long appointmentId)
    {
        return success(appointmentsService.selectAppointmentsByAppointmentId(appointmentId));
    }

    /**
     * 新增挂号建档
     */
    @RequiresPermissions("dental:appointments:add")
    @Log(title = "挂号建档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Appointments appointments)
    {
        return toAjax(appointmentsService.insertAppointments(appointments));
    }

    /**
     * 修改挂号建档
     */
    @RequiresPermissions("dental:appointments:edit")
    @Log(title = "挂号建档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Appointments appointments)
    {
        return toAjax(appointmentsService.updateAppointments(appointments));
    }

    /**
     * 删除挂号建档
     */
    @RequiresPermissions("dental:appointments:remove")
    @Log(title = "挂号建档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appointmentIds}")
    public AjaxResult remove(@PathVariable Long[] appointmentIds)
    {
        return toAjax(appointmentsService.deleteAppointmentsByAppointmentIds(appointmentIds));
    }


}
