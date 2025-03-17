package com.ruoyi.dental.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.dental.service.IDoctorsService;
import com.ruoyi.system.api.domain.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医生信息Controller
 * 
 * @author zh
 * @date 2025-03-15
 */
@RestController
@RequestMapping("/doctors")
@Tag(name = "医生信息")
public class DoctorsController extends BaseController
{
    @Autowired
    private IDoctorsService doctorsService;

    /**
     * 查询医生信息列表
     */
    @RequiresPermissions("dental:doctors:list")
    @GetMapping("/list")
    public TableDataInfo list(Doctors doctors)
    {
        startPage();
        List<Doctors> list = doctorsService.selectDoctorsList(doctors);
        return getDataTable(list);
    }

    /**
     * 导出医生信息列表
     */
    @RequiresPermissions("dental:doctors:export")
    @Log(title = "医生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Doctors doctors)
    {
        List<Doctors> list = doctorsService.selectDoctorsList(doctors);
        ExcelUtil<Doctors> util = new ExcelUtil<Doctors>(Doctors.class);
        util.exportExcel(response, list, "医生信息数据");
    }

    /**
     * 获取医生信息详细信息
     */
    @RequiresPermissions("dental:doctors:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(doctorsService.selectDoctorsById(id));
    }

    /**
     * 新增医生信息
     */
    @RequiresPermissions("dental:doctors:add")
    @Log(title = "医生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Doctors doctors)
    {
        return toAjax(doctorsService.insertDoctors(doctors));
    }



    /**
     * 修改医生信息
     */
    @RequiresPermissions("dental:doctors:edit")
    @Log(title = "医生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Doctors doctors)
    {
        return toAjax(doctorsService.updateDoctors(doctors));
    }

    /**
     * 删除医生信息
     */
    @RequiresPermissions("dental:doctors:remove")
    @Log(title = "医生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(doctorsService.deleteDoctorsByIds(ids));
    }

    /**
     * 根据用户信息插入医生信息
     * @param sysUser
     * @return
     */
    @PostMapping("/insert/sysUser")
    @Operation(summary = "根据用户信息插入医生信息")
    @InnerAuth
    public R insertBySysUser(@RequestBody SysUser sysUser){
        return doctorsService.insertBySysUser(sysUser);
    }
}
