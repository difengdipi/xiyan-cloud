package com.ruoyi.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/

@Aspect
@Component
@Slf4j
public class AspectTime {

    //我这里需要需拦截的是myabtis-中insert和update方法
    @Pointcut("execution(* com.ruoyi.shop.service.*.*.save(..))")
    public void insert() {
    }

    @Pointcut("execution(* com.ruoyi.shop.service.*.*.update(..))")
    public void update() {
    }

    @Pointcut("execution(* com.ruoyi.shop.service.*.*.saveBatch(..))")
    public void saveBatch() {
    }

    @Before(value = "insert() && saveBatch()")
    public void insertTime(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            int length = args.length;
            // 假设第一个参数是实体对象
            try {
                for (int i = 0; i < length; i++) {
                    Object entity = args[i];
                    Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                    setCreateTime.invoke(entity, LocalDateTime.now());
                    setUpdateTime.invoke(entity, LocalDateTime.now());
                }
            } catch (Exception e) {
                // 记录日志而不是抛出运行时异常
            }
        }
    }

    @Before(value = "update()")
    public void updateTime(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object entity = args[0]; // 假设第一个参数是实体对象
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                setUpdateTime.invoke(entity, LocalDateTime.now());
            } catch (Exception e) {
                // 记录日志而不是抛出运行时异常
            }
        }
    }
}