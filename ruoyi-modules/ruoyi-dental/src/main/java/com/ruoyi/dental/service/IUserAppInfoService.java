package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.AppDetailVo;
import com.ruoyi.dental.domain.vo.AppNumVo;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/20
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public interface IUserAppInfoService extends IService<UserAppInfo> {
    List<AppNumVo> getDoctorAppNum();


    List<AppDetailVo> AllApplist(Long userId);
}
