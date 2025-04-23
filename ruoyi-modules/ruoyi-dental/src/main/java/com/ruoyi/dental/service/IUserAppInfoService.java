package com.ruoyi.dental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.dto.AdminUserAppinfoDto;
import com.ruoyi.dental.domain.vo.AppDetailVo;
import com.ruoyi.dental.domain.vo.AppNumVo;
import com.ruoyi.dental.domain.vo.UserAppInfoDto;

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


    /**
     * 查询预约
     *
     * @param id 预约主键
     * @return 预约
     */
    public UserAppInfo selectUserAppInfoById(Long id);

    /**
     * 查询预约列表
     *
     * @param userAppInfo 预约
     * @return 预约集合
     */
    public List<UserAppInfoDto> selectUserAppInfoList(UserAppInfoDto userAppInfo);

    /**
     * 新增预约
     *
     * @param userAppInfo 预约
     * @return 结果
     */
    public int insertUserAppInfo(UserAppInfo userAppInfo);

    /**
     * 修改预约
     *
     * @param userAppInfo 预约
     * @return 结果
     */
    public int updateUserAppInfo(UserAppInfo userAppInfo);

    /**
     * 批量删除预约
     *
     * @param ids 需要删除的预约主键集合
     * @return 结果
     */
    public int deleteUserAppInfoByIds(Long[] ids);

    /**
     * 删除预约信息
     *
     * @param id 预约主键
     * @return 结果
     */
    public int deleteUserAppInfoById(Long id);

    List<AdminUserAppinfoDto> listschedule();

}
