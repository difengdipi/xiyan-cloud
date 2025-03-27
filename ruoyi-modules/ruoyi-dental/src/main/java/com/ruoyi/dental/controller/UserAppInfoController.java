package com.ruoyi.dental.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.doctorSchedules;
import com.ruoyi.dental.domain.vo.AppDetailReasonDto;
import com.ruoyi.dental.domain.vo.AppDetailVo;
import com.ruoyi.dental.service.IUserAppInfoService;
import com.ruoyi.dental.service.IdoctorSchedules;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/20
 * @Project_name : RuoYi-Cloud
 * @Version :
 *
 **/
@RestController
@RequestMapping("/UserAooInfo")
@Slf4j
public class UserAppInfoController {
    @Autowired
    IUserAppInfoService userAppInfoService;

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
     *  添加用户病例预约信息
     * @param appUserInfo
     * @return
     */
    @Operation(summary= "添加用户病例预约信息")
    @PutMapping("/add")
    public R addAppUserInfo(@RequestBody UserAppInfo appUserInfo){
        Long userid = DentalUtils.getUserId();
        appUserInfo.setUserId(userid);
        log.info("添加用户病例预约信息:{}",appUserInfo);
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
        log.info("查询用户的全部预约:{}",AppDetailVolsit);
        return R.ok(AppDetailVolsit);
    }

    @Autowired
    IdoctorSchedules doctorSchedulesService;
    @Operation(summary = "查询用户的预约详细信息")
    @GetMapping("/byId/{id}")
    public R geetUserByid(@PathVariable("id")Long id){
        UserAppInfo byId = userAppInfoService.getById(id);
        AppDetailVo appDetailVo = new AppDetailVo();
        BeanUtils.copyProperties(byId,appDetailVo);
        doctorSchedules one = doctorSchedulesService.getOne(new LambdaQueryWrapper<doctorSchedules>()
                .eq(doctorSchedules::getId, byId.getScheduleId()));
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
        log.info("查询用户所创建的全部患者信息：{}",list);
        return R.ok(list);
    }
}
