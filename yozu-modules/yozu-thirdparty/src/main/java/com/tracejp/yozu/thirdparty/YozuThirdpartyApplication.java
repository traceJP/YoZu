package com.tracejp.yozu.thirdparty;

import com.tracejp.yozu.common.security.annotation.EnableCustomConfig;
import com.tracejp.yozu.common.security.annotation.EnableYozuFeignClients;
import com.tracejp.yozu.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableYozuFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class YozuThirdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YozuThirdpartyApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  第三方服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
