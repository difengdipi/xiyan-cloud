package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.AppNumVo;
import com.ruoyi.dental.mapper.UserAppInfoMapper;
import com.ruoyi.dental.service.IUserAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/20
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Service
public class UserAppInfoServiceImpl extends ServiceImpl<UserAppInfoMapper, UserAppInfo> implements IUserAppInfoService {
    @Autowired
    UserAppInfoMapper userAppInfoMapper;
    @Override
    public List<AppNumVo> getDoctorAppNum() {
        return userAppInfoMapper.getDoctorAppNum();
    }
}
