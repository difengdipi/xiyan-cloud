package com.zh.cn.controller;

import com.zh.cn.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/1
 * @Project_name : data
 * @Version :
 **/

@RestController
@RequestMapping("")
public class CoreController {
    @GetMapping("/starter")
    public Result starter(@RequestBody String str){
        //从头开始启动
        return Result.success();
    }
    @GetMapping("/stop")
    public Result stop(){
        //停止
        return Result.success();
    }
    @GetMapping("/restart")
    public Result restart(){
        //重启----从当前进度开始
        return Result.success();
    }
}
