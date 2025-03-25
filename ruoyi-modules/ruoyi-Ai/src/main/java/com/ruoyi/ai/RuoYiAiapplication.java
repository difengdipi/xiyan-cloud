package com.ruoyi.ai;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/2/26
 * @Project_name : spring-Ai
 * @Version :
 **/
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class RuoYiAiapplication {
    public static void main(String[] args) {
        SpringApplication.run(RuoYiAiapplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Ai模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
