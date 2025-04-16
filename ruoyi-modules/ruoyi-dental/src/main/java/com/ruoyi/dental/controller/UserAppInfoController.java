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
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.DoctorSchedules;
import com.ruoyi.dental.domain.Dto.AdminUserAppinfoDto;
import com.ruoyi.dental.domain.Patients;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.AppDetailReasonDto;
import com.ruoyi.dental.domain.vo.AppDetailVo;
import com.ruoyi.dental.domain.vo.UserAppInfoDto;
import com.ruoyi.dental.service.IDoctorSchedulesService;
import com.ruoyi.dental.service.IPatientsService;
import com.ruoyi.dental.service.IUserAppInfoService;
import com.ruoyi.dental.utils.SensiUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/20
 * @Project_name : RuoYi-Cloud
 * @Version :
 *
 **/
@RestController
@RequestMapping("/UserAppInfo")
@Slf4j
public class UserAppInfoController extends BaseController {
    @Autowired
    IUserAppInfoService userAppInfoService;
    @Resource
    IPatientsService patientsService;
    @Autowired
    IDoctorSchedulesService doctorSchedulesService;

    /**
     *  根据用户id查询所创建的用户的病例
     * @param id
     * @return
     */
    @Operation(summary= "根据用户id查询所创建的用户的病例")
    @GetMapping("/get/{id}")
    public R getAppUserInfo(@PathVariable("id") Long id){
        List<UserAppInfo> list = userAppInfoService.list(new LambdaQueryWrapper<UserAppInfo>()
                .eq(UserAppInfo::getUserId, id));
        return R.ok(list);
    }

    /**
     * 今日是否存在重复数据
     * @param appUserInfo
     * @return
     */
    public R isExist(UserAppInfo appUserInfo){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = calendar.getTime();

        if(userAppInfoService.list(new LambdaQueryWrapper<UserAppInfo>()
                .eq(UserAppInfo::getIdcard, appUserInfo.getIdcard())
                .eq(UserAppInfo::getPhone, appUserInfo.getPhone())
                .ge(UserAppInfo::getCreateTime, startOfDay)
                .lt(UserAppInfo::getCreateTime, endOfDay)
        ).size() > 0) {
            return R.fail("您今日已有预约，请勿重复预约");
        }else{
            return R.ok();
        }
    }
    /**
     *  添加用户病例预约信息
     * @param appUserInfo
     * @return
     */
    @Operation(summary= "添加用户病例预约信息")
    @PutMapping("/add")
    @Transactional
    public R addAppUserInfo(@RequestBody UserAppInfo appUserInfo){
        Long userid = DentalUtils.getUserId();
        appUserInfo.setUserId(userid);
        appUserInfo.setCreateTime(new Date());
        appUserInfo.setStatus((short)0);
        //判断该用户今日是否有预约
        if(isExist(appUserInfo).getCode() == 500){
            return isExist(appUserInfo);
        }
        // 使用数据库版本进行
        int update = doctorSchedulesService.updateByScheduleId(appUserInfo.getScheduleId());
        if(update <= 0){
            //将当前的行程状态改为不可预约
            boolean update1 = doctorSchedulesService.update(new LambdaUpdateWrapper<DoctorSchedules>()
                    .eq(DoctorSchedules::getId, appUserInfo.getScheduleId())
                    .set(DoctorSchedules::getStatus, 0)
            );
            return R.fail("预约失败,当前主治医生预约已满");
        }
        CompletableFuture.runAsync(()-> {
            doctorSchedulesService.update(new LambdaUpdateWrapper<DoctorSchedules>()
                    .eq(DoctorSchedules::getId, appUserInfo.getScheduleId())
                    .set(DoctorSchedules::getAppNum, doctorSchedulesService.getById(appUserInfo.getScheduleId()).getAppNum()+1)
            );
            //同时创建患者--需要判断患者是否存在
            if(appUserInfo.getPhone() != null){
                Patients one = patientsService.getOne(new LambdaQueryWrapper<Patients>()
                        .eq(Patients::getPhoneNumber, appUserInfo.getPhone().toString())
                );
                LambdaUpdateWrapper<UserAppInfo> wrapper = new LambdaUpdateWrapper<>();
                if(one != null){
                    appUserInfo.setPatientId(one.getPatientId());
                    wrapper.set(UserAppInfo::getPatientId, one.getPatientId()).eq(UserAppInfo::getId, appUserInfo.getId());
                    userAppInfoService.update(wrapper);
                    return ;
                }
                else{
                    Patients build = Patients.builder()
                            .userName(appUserInfo.getName())
                            .age(SensiUtils.getAge(appUserInfo.getIdcard()))
                            .gender(SensiUtils.getSec(appUserInfo.getIdcard()))
                            .phoneNumber(appUserInfo.getPhone().toString())
                            .createdTime(new Date())
                            .build();
                    patientsService.save(build);
                    wrapper.set(UserAppInfo::getPatientId, build.getPatientId()).eq(UserAppInfo::getId, appUserInfo.getId());
                    userAppInfoService.update(wrapper);
                }
            }
        }).whenCompleteAsync((v,e)->{
            if (e != null) {
                log.error("异步调用失败", e);
            }
            if(v != null){
                log.info("异步调用成功");
            }
        });
        userAppInfoService.save(appUserInfo);
        return R.ok(appUserInfo.getId());
    }

    @Operation(summary = "更新用户病例预约信息")
    @PutMapping("/update")
    public R updateAppUserInfo(@RequestBody UserAppInfo appUserInfo){
        return R.ok(userAppInfoService.updateById(appUserInfo)? "修改成功" : "修改失败");
    }
    @Operation(summary = "删除用户病例预约信息")
    @DeleteMapping("/delete")
    public R deleteAppUserInfo(@RequestBody List<UserAppInfo> appUserInfo){
        return userAppInfoService.removeBatchByIds(appUserInfo)? R.ok("删除成功") : R.fail("删除失败");

    }

    @Operation(summary = "查询用户的全部预约")
    @GetMapping("/list")
    public R getAllAppUserInfo(){
        Long userId = DentalUtils.getUserId();
        //根据用户id查询全部的预约信息表
        List<AppDetailVo> AppDetailVolsit = userAppInfoService.AllApplist(userId);
        return R.ok(AppDetailVolsit);
    }

    @Operation(summary = "查询用户的预约详细信息")
    @GetMapping("/byId/{id}")
    public R geetUserByid(@PathVariable("id")Long id){
        UserAppInfo byId = userAppInfoService.getById(id);
        AppDetailVo appDetailVo = new AppDetailVo();
        BeanUtils.copyProperties(byId,appDetailVo);
        DoctorSchedules one = doctorSchedulesService.getOne(new LambdaQueryWrapper<DoctorSchedules>()
                .eq(DoctorSchedules::getId, byId.getScheduleId()));
        appDetailVo.setDate(one.getDate());
        return R.ok(appDetailVo);
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "取消预约")
    public R cancelAppUserInfo(@PathVariable("id")Long id,@RequestBody AppDetailReasonDto dto){
        boolean update = userAppInfoService.update(new LambdaUpdateWrapper<UserAppInfo>()
                .eq(UserAppInfo::getId, id)
                .set(UserAppInfo::getCancelReason, dto.getReason())
                .set(UserAppInfo::getStatus, 2)
        );
        return update? R.ok("取消成功") : R.fail("取消失败");
    }

    @GetMapping("/list/ById")
    @Operation(summary = "查询用户所创建的全部患者信息")
    public  R getAppUserInfoByUserId( ){
        Long userId = DentalUtils.getUserId();
        List<UserAppInfo> list = userAppInfoService.list(new LambdaQueryWrapper<UserAppInfo>().eq(UserAppInfo::getUserId,userId));
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if(map.isEmpty()){
                map.put(list.get(i).getIdcard(),map.getOrDefault(list.get(i).getIdcard(),0));
                continue;
            }
            if(map.containsKey(list.get(i).getIdcard())){
                log.info("移除的信息：{}",list.get(i).getIdcard());
                list.remove(i);
            }else {
                map.put(list.get(i).getIdcard(),1);
            }
        }
        return R.ok(list);
    }



    /**
     * 查询预约列表
     */
    @RequiresPermissions("dental:UserAppInfo:list")
    @GetMapping("/admin/list")
    public TableDataInfo list(UserAppInfoDto userAppInfo)
    {
        startPage();
        List<UserAppInfoDto> list = userAppInfoService.selectUserAppInfoList(userAppInfo);
        return getDataTable(list);
    }

    /**
     * 导出预约列表
     */
    @RequiresPermissions("dental:UserAppInfo:export")
    @Log(title = "预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAppInfoDto userAppInfo)
    {
        List<UserAppInfoDto> list = userAppInfoService.selectUserAppInfoList(userAppInfo);
        ExcelUtil<UserAppInfoDto> util = new ExcelUtil<UserAppInfoDto>(UserAppInfoDto.class);
        util.exportExcel(response, list, "预约数据");
    }

    /**
     * 获取预约详细信息
     */
    @RequiresPermissions("dental:UserAppInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userAppInfoService.selectUserAppInfoById(id));
    }

    /**
     * 修改预约
     */
    @RequiresPermissions("dental:UserAppInfo:edit")
    @Log(title = "预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAppInfo userAppInfo)
    {
        return toAjax(userAppInfoService.updateUserAppInfo(userAppInfo));
    }

    /**
     * 删除预约
     */
    @Log(title = "预约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids)
    {
        Long userId = DentalUtils.getUserId();
        //用户端的删除只是删除了他所对应的用户的预约信息，但是数据库中的预约信息还在，所以需要做逻辑删除
        boolean update = userAppInfoService.update(new LambdaUpdateWrapper<UserAppInfo>()
                .in(UserAppInfo::getId, ids)
                .eq(UserAppInfo::getUserId, userId)
                .set(UserAppInfo::getUserId, null)
        );
        log.info("删除预约信息:{}",ids);
        return toAjax(update);
    }

    @GetMapping("/all")
    @Operation(summary = "获取全部预约数据")
    public R getAll(){
        return R.ok(userAppInfoService.list());
    }


    @Operation(summary = "患者饼状图")
    @GetMapping("/picture")
    public R<List<AdminUserAppinfoDto>> schedule(){
        List<AdminUserAppinfoDto> list = userAppInfoService.listschedule();
        return R.ok(list);
    }

}
