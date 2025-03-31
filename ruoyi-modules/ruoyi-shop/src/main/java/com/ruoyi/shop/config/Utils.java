package com.ruoyi.shop.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/31
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public class Utils {
    public static final List<String> split(String s){
        return Arrays.stream(s.split(",")).collect(Collectors.toList());
    }

}
