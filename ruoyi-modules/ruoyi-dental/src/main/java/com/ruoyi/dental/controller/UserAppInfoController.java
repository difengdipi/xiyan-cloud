package com.ruoyi.dental.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.service.IUserAppInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
