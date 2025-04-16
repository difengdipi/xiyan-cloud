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
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.Doctors;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.DoctorSchedulesVo;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import com.ruoyi.dental.service.IDoctorsService;
import com.ruoyi.dental.service.IUserAppInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 医生行程Controller
 * 
 * @author zh
 * @date 2025-04-03
 */
@RestController
@RequestMapping("/schedules")
@Tag(name = "医生行程安排")
@Slf4j
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
    public TableDataInfo list(DoctorSchedulesVo DoctorSchedulesVo)
    {
        startPage();
        List<DoctorSchedulesVo> list = doctorSchedulesService.selectDoctorSchedulesList(DoctorSchedulesVo);
        return getDataTable(list);
    }

    /**
     * 导出医生行程列表
     */
    @RequiresPermissions("dental:schedules:export")
    @Log(title = "医生行程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出医生行程列表")
    public void export(HttpServletResponse response, DoctorSchedulesVo doctorSchedules)
    {
        log.info("导出医生行程列表:{}", doctorSchedules);
        List<DoctorSchedulesVo> list = doctorSchedulesService.selectDoctorSchedulesList(doctorSchedules);
        ExcelUtil<DoctorSchedulesVo> util = new ExcelUtil<DoctorSchedulesVo>(DoctorSchedulesVo.class);
        util.exportExcel(response, list, "医生行程数据");
    }
    @Autowired
    private IDoctorsService doctorService;
    /**
     * 导入医生行程
     * @param file
     * @return
     */
    @SneakyThrows
    @PostMapping("/import")
    @RequiresPermissions("dental:schedules:import")
    @Log(title = "医生行程", businessType = BusinessType.IMPORT)
    @Operation(summary = "导入医生行程")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file){
        ExcelUtil<DoctorSchedulesVo> ExcelUtil = new ExcelUtil<>(DoctorSchedulesVo.class);
        List<DoctorSchedulesVo> doctorSchedulesVos = ExcelUtil.importExcel(file.getInputStream());
        //批量导入数据
        //构造出对应的DoctorSchedules对象
        //根据userId查询出对应的doctors中的信息
        CompletableFuture.runAsync(()->{
                Set<Long> collect = doctorSchedulesVos.stream().map(DoctorSchedulesVo::getUserId).collect(Collectors.toSet());
                //根据id获取出对应的医生信息
                List<Doctors> list = doctorService.list(new LambdaQueryWrapper<Doctors>()
                        .in(Doctors::getUserId, collect)
                );
                Map<Long, Doctors> map = list.stream()
                        .collect(Collectors.toMap(Doctors::getUserId, doctors -> doctors));
                List<DoctorSchedulesVo> doctorSchedulesVoslist = doctorSchedulesVos.stream().map(s -> {
                    Doctors doctors = map.get(s.getUserId());
                    if(ObjectUtils.isEmpty(doctors)){
                        return s;
                    }
                    s.setDoctorName(doctors.getName());
                    s.setDoctorId(doctors.getId());
                    s.setCreateTime(LocalDateTime.now());
                    return s;
                }).collect(Collectors.toList());
                    ArrayList< DoctorSchedules> objects = new ArrayList<>();

                    doctorSchedulesVoslist.stream().forEach(doctorSchedulesVo -> {
                        DoctorSchedules build = DoctorSchedules.builder()
                                .doctorId(doctorSchedulesVo.getDoctorId())
                                .date(doctorSchedulesVo.getDate())
                                .status(doctorSchedulesVo.getStatus())
                                .maxNum(doctorSchedulesVo.getMaxNum())
                                .createTime(doctorSchedulesVo.getCreateTime())
                                .build();
                        LambdaQueryWrapper<DoctorSchedules> eq = new LambdaQueryWrapper<DoctorSchedules>()
                                .eq(DoctorSchedules::getDoctorId, build.getDoctorId())
                                .eq(DoctorSchedules::getDate, build.getDate());
                        DoctorSchedules one = doctorSchedulesService.getOne(eq);
                        if( one != null){
                            LambdaUpdateWrapper<DoctorSchedules> set = new LambdaUpdateWrapper<DoctorSchedules>()
                                    .eq(DoctorSchedules::getDoctorId, one.getDoctorId())
                                    .eq(DoctorSchedules::getDate, one.getDate())
                                    .set(DoctorSchedules::getUpdateTime, LocalDateTime.now())
                                    .set(DoctorSchedules::getStatus, build.getStatus())
                                    .set(DoctorSchedules::getMaxNum, build.getMaxNum())
                                    .set(DoctorSchedules::getStatus, build.getStatus());
                                //更新数据--只更新状态
                                doctorSchedulesService.update(set);
                        }else{
                            objects.add(build);
                        }
                    });
                    doctorSchedulesService.saveBatch(objects);
                })
        .whenCompleteAsync((s,e)->{
            if(e!=null){
                log.error("导入医生行程失败",e);
            }
            if(s!=null){
            }
        });

        return success();
    }
    @PostMapping("/template")
    @RequiresPermissions("dental:schedules:export")
    @Operation(summary = "导出行程模版")
    public void exportTemplate(HttpServletResponse response){
        List<DoctorSchedulesVo> list = new ArrayList<>();
        list.add(new DoctorSchedulesVo());
        ExcelUtil<DoctorSchedulesVo> util = new ExcelUtil<DoctorSchedulesVo>(DoctorSchedulesVo.class);
        util.exportExcel(response, list, "医生行程模版");
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

    @Autowired
    IUserAppInfoService userAppInfoService;

    @RequiresPermissions("dental:schedules:show")
    @GetMapping("/show")
    @Operation(summary = "获取医生行程取消原因")
    public R showReason(@RequestParam(required = true)Long id){
        //通过行程id号去同步用户预约的表
        UserAppInfo one = userAppInfoService.getOne(new LambdaQueryWrapper<UserAppInfo>()
                .eq(UserAppInfo::getScheduleId, id)
        );
        return R.ok(one.getCancelReason());
    }


    /**
     * 新增医生行程
     */
    @RequiresPermissions("dental:schedules:add")
    @Log(title = "医生行程", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "新增医生行程")
    public AjaxResult add(@RequestBody DoctorSchedulesVo doctorSchedules)
    {
        DoctorSchedules doctorSchedules1 = new DoctorSchedules();
        BeanUtils.copyProperties(doctorSchedules, doctorSchedules1);
        doctorSchedules1.setCreateTime(LocalDateTime.now());
        return toAjax(doctorSchedulesService.save(doctorSchedules1));
    }

    /**
     * 修改医生行程
     */
    @RequiresPermissions("dental:schedules:edit")
    @Log(title = "医生行程", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改医生行程")

    public AjaxResult edit(@RequestBody DoctorSchedulesVo doctorSchedules)
    {
        log.info("医生修改原因：{}",doctorSchedules);

        return toAjax(doctorSchedulesService.updateDoctorSchedules(doctorSchedules));
    }

    /**
     * 删除医生行程
     */
    @RequiresPermissions("dental:schedules:remove")
    @Log(title = "医生行程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @Operation(summary = "删除医生行程")
    public AjaxResult remove(@PathVariable("ids") Long[] ids,@RequestParam(required = true, defaultValue = "店铺容量满，请到店挂号")String reason)
    {

        return toAjax(doctorSchedulesService.deleteDoctorSchedulesByIds(ids,reason));
    }
    @GetMapping("/all")
    @Operation(summary = "获取今日的全部预约数据")
    public R getNum(){
        //获取今日预约数量，//直接去获取医生行程的预约量就可以
        LocalDate today = LocalDate.now();
        List<DoctorSchedules> list = doctorSchedulesService.list(
                new LambdaQueryWrapper<DoctorSchedules>()
                        .eq(DoctorSchedules::getDate, today)
        );
        Integer i = 0;
        if(list.isEmpty()){
            i = 0 ;
        }else {
            i = list.stream().map(DoctorSchedules::getAppNum).reduce(Integer::sum).get();
        }
        return R.ok(i);
    }
}
