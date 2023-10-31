package com.yangbiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yangbiao.mapper") //用于指定要扫描的包路径，以便将其中的接口映射到对应的实现类
@EnableScheduling //定时任务
@EnableSwagger2
public class SanGengBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanGengBlogApplication.class,args);
    }
}
