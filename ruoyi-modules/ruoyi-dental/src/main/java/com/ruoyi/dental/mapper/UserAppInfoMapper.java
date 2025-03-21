package com.ruoyi.dental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.dental.domain.UserAppInfo;
import com.ruoyi.dental.domain.vo.AppNumVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/20
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Mapper
public interface UserAppInfoMapper extends BaseMapper<UserAppInfo> {
    List<AppNumVo> getDoctorAppNum();

}
