package com.ruoyi.dental.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.dto.DoctorNumsDto;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import com.ruoyi.dental.service.IDoctorsService;
import com.ruoyi.dental.service.IUserAppInfoService;
import com.ruoyi.system.api.domain.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 医生信息Controller
 * 
 * @author zh
 * @date 2025-03-15
 */
@RestController
@RequestMapping("/doctors")
@Tag(name = "医生信息")
@Slf4j
public class DoctorsController extends BaseController
{
    @Autowired
    private IDoctorsService doctorsService;

    /**
     * 查询医生信息列表
     */
    @RequiresPermissions("dental:doctors:list")
    @GetMapping("/list")
    @Operation(summary = "查询医生信息列表")
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
    @Operation(summary = "导出医生信息列表")
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
    @Operation(summary = "获取医生信息详细信息")
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
    @Operation(summary = "新增医生信息")
    @PostMapping
    public AjaxResult add(@RequestBody Doctors doctors)
    {
        doctors.setDeptId(getDeptId());
        return toAjax(doctorsService.insertDoctors(doctors));
    }

    /**
     * 修改医生信息
     */
    @RequiresPermissions("dental:doctors:edit")
    @Log(title = "医生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改医生信息")
    public AjaxResult edit(@RequestBody Doctors doctors)
    {
        return toAjax(doctorsService.updateDoctors(doctors));
    }

    /**
     * 删除医生信息
     */
    @Operation(summary = "删除医生信息")
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

    @Operation(summary = "获取今日在线医生")
    @GetMapping("/infos")
    public R getDockersInfo(){
        //小程序端获取今日在线医生，需要获取的是今日或者未来行程安排中拥有的而不是全部渲染
        List<Doctors> list = doctorsService.list(new LambdaQueryWrapper<Doctors>()
                .in(Doctors::getStatus,1,3)
        );
        //判断当前时间是否超过18:00，超过则不显示
        LocalDate today = LocalDate.now();
        if(LocalDateTime.now().getHour()>=18){
            today = today.plusDays(1);
        }
        LocalDate tomorrow = today.plusDays(14);
        List<DoctorSchedules> schedulesList = doctorSchedulesService.list(
                new LambdaQueryWrapper<DoctorSchedules>()
                        .between(DoctorSchedules::getDate, today, tomorrow)
                        .notIn(DoctorSchedules::getStatus, 0)
        );
        //获取行程表中去重后的医生id
        Set<Long> collect = schedulesList.stream().map(DoctorSchedules::getDoctorId).collect(Collectors.toSet());
        //根据去重后的医生id获取医生信息 
        List<Doctors> collect1 = list.stream().filter(doctors -> collect.contains(doctors.getId())).collect(Collectors.toList());
        return  R.ok(collect1);
    }

    @Operation(summary = "根据医生id获取医生信息")
    @GetMapping("/infos/byId/{id}")
    public R getDockerInfo(@PathVariable("id") Long id){
        Doctors doctors = doctorsService.getById(id);
        return  R.ok(doctors);
    }
    @Autowired
    IDoctorSchedulesService doctorSchedulesService;
    @Operation(summary = "根据医生id获取医生未来两周的时间安排表")
    @GetMapping("/AppointSchedule/{id}")
    public R getAppointmentSchedule(@PathVariable("id")Long id)
    {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        // 固定早上8点作为一天的开始
        LocalDateTime todayStart = today.atTime(8, 0);
        // 设置查询结束时间为14天后
        LocalDateTime endDateTime = today.plusDays(14).atTime(23, 59, 59);
        // 调整查询开始时间
        List<Integer> status = null;
        if (now.getHour() >= 18) {
            // 18点后，预约从明天8点开始
            todayStart = today.plusDays(1).atTime(8, 0);
            endDateTime = today.plusDays(15).atTime(23, 59, 59);
        } else if (now.getHour() > 9) {
            // 9点后但未到18点，从当前时间开始查询
            todayStart = now;
            status = Arrays.asList(2, 3);
        }
        Date startDate = Date.from(todayStart.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
        LambdaQueryWrapper<DoctorSchedules> between = new LambdaQueryWrapper<DoctorSchedules>()
                .eq(DoctorSchedules::getDoctorId, id)
                .between(DoctorSchedules::getDate, startDate, endDate);
        // 查询数据库
        if(!(status == null)){
            between.in(DoctorSchedules::getStatus, status);
        }
        List<DoctorSchedules> list = doctorSchedulesService.list(
                between
        );
        return R.ok(list);
    }
    @Autowired
    IUserAppInfoService userAppInfoService;
    @Operation(summary = "根据预约id获取医生信息")
    @GetMapping("/infos/byAppId/{id}")
    public R getDentalInfo(@PathVariable("id")Long id)
    {
        UserAppInfo byId = userAppInfoService.getById(id);
        Doctors doctors = doctorsService.getById(byId.getDoctorId());
        return R.ok(doctors);
    }


    @Operation(summary = "更新预约人数")
    public R updateAppList(List<DoctorNumsDto> dto){
        dto.stream().map(tmp -> {
            DoctorNumsDto.type fun = tmp.getFun();
            LambdaUpdateWrapper<Doctors> LambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            if(fun.equals(DoctorNumsDto.type.add)){
                LambdaUpdateWrapper.setSql("app_num = app_num + 1");
            }
            if(fun.equals(DoctorNumsDto.type.cancel)){
                LambdaUpdateWrapper.setSql("app_num = app_num - 1");
            }
            boolean update = doctorsService.update(LambdaUpdateWrapper);
            return update;
        });

        return R.ok();
    }
}
