package com.ruoyi.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public ListTypeHandler listTypeHandler() {
        return new ListTypeHandler();
    }
}