package com.ruoyi.dental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.datascope.annotation.DataScope;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.dto.AdminUserAppinfoDto;
import com.ruoyi.dental.domain.vo.AppDetailVo;
import com.ruoyi.dental.domain.vo.AppNumVo;
import com.ruoyi.dental.domain.vo.UserAppInfoDto;
import com.ruoyi.dental.mapper.UserAppInfoMapper;
import com.ruoyi.dental.service.IUserAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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


    /**
     * 查询预约
     *
     * @param id 预约主键
     * @return 预约
     */
    @Override
    public UserAppInfo selectUserAppInfoById(Long id)
    {
        return userAppInfoMapper.selectUserAppInfoById(id);
    }

    /**
     * 查询预约列表
     *
     * @param userAppInfo 预约
     * @return 预约
     */
    @Override
    @DataScope(deptAlias = "td", userAlias = "td")
    public List<UserAppInfoDto> selectUserAppInfoList(UserAppInfoDto userAppInfo)
    {
        return userAppInfoMapper.selectUserAppInfoList(userAppInfo);
    }

    /**
     * 新增预约
     *
     * @param userAppInfo 预约
     * @return 结果
     */
    @Override
    public int insertUserAppInfo(UserAppInfo userAppInfo)
    {
        userAppInfo.setCreateTime(new Date());
        return userAppInfoMapper.insertUserAppInfo(userAppInfo);
    }

    /**
     * 修改预约
     *
     * @param userAppInfo 预约
     * @return 结果
     */
    @Override
    public int updateUserAppInfo(UserAppInfo userAppInfo)
    {
        userAppInfo.setUpdateTime(new Date());
        return userAppInfoMapper.updateUserAppInfo(userAppInfo);
    }

    /**
     * 批量删除预约
     *
     * @param ids 需要删除的预约主键
     * @return 结果
     */
    @Override
    public int deleteUserAppInfoByIds(Long[] ids)
    {
        return userAppInfoMapper.deleteUserAppInfoByIds(ids);
    }

    /**
     * 删除预约信息
     *
     * @param id 预约主键
     * @return 结果
     */
    @Override
    public int deleteUserAppInfoById(Long id)
    {
        return userAppInfoMapper.deleteUserAppInfoById(id);
    }

    @Override
    public List<AdminUserAppinfoDto> listschedule() {
        return userAppInfoMapper.listschedule();
    }

    @Override
    public List<UserAppInfoDto> selectList(UserAppInfoDto userAppInfo) {
        return userAppInfoMapper.list(userAppInfo);
    }

    @Override
    public List<AppNumVo> getDoctorAppNum() {
        return userAppInfoMapper.getDoctorAppNum();
    }

    @Override
    public List<AppDetailVo> AllApplist(Long userId) {
        return userAppInfoMapper.AppDetailVolsit(userId);
    }
}
