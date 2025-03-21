package com.ruoyi.common.security.Util;

import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.security.utils.SecurityUtils;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/21
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public class DentalUtils {
    public static  final Long getUserId(){
        return Long.parseLong(JwtUtils.getUserId(SecurityUtils.getToken()));
    }
}
