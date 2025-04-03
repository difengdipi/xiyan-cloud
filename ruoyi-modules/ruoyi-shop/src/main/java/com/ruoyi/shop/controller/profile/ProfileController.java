package com.ruoyi.shop.controller.profile;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.Util.DentalUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.profile.ProfileDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:  小程序个人中心控制类
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@RestController
@RequestMapping("/member/profile")
@Tag(name = "小程序个人中心控制类")
@Slf4j
public class ProfileController {

    @Autowired
    RemoteUserService remoteUserService;

    @Operation(summary = "获取个人信息")
    @GetMapping("")
    public R<ProfileDetail> getMemberProfile(){
        Long userId = DentalUtils.getUserId();
        SysUser data = remoteUserService.getAppUserInfo(userId, SecurityConstants.INNER).getData();
        // 构造 用户信息
        ProfileDetail profileDetail = new ProfileDetail();
        profileDetail.setId(userId);
        profileDetail.setAvatar(data.getAvatar());
        profileDetail.setAccount(data.getUserName());
        profileDetail.setNickname(data.getNickName());
        profileDetail.setGender(data.getSex());
        return R.ok(profileDetail);
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("")
    public R putMemberProfile(@RequestBody ProfileDetail profileDetail){
        Long userId = DentalUtils.getUserId();
        profileDetail.setId(userId);
        SysUser sysUser = remoteUserService.updateAppUserInfo(profileDetail, SecurityConstants.INNER).getData();
        ProfileDetail.ProfileDetailBuilder builder = ProfileDetail.builder();
        if(StringUtils.isNotEmpty(sysUser.getAvatar())){
            builder.avatar(sysUser.getAvatar());
        }
        if(StringUtils.isNotEmpty(sysUser.getNickName())){
            builder.nickname(sysUser.getNickName());
        }
        if(StringUtils.isNotEmpty(sysUser.getSex())){
            builder.gender(sysUser.getSex());
        }
        if(StringUtils.isNotEmpty(sysUser.getUserName())){
            builder.account(sysUser.getUserName());
        }
        return R.ok(builder.build());
    }
}
