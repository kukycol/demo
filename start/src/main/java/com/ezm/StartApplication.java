package com.ezm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@MapperScan({"com.ezm.dao.mapper"})
@SpringBootApplication
public class StartApplication {


    private static String druidPattern;

    private static String port;


    @Value("${server.port}")
    private void setPort(String port) {
        this.port = port;
    }


    @Value("${spring.datasource.druid.stat-view-servlet.url-pattern}")
    private void setDruidPattern(String druidPattern) {
        this.druidPattern = druidPattern;
    }


    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
        //接口文档
        log.warn("swagger: http://localhost:" + port + "/doc.html");
        //springboot 项目监控
        log.warn("actuator: http://localhost:" + port + "/actuator/manage");
        //druid 连接池
        log.warn("druid: http://localhost:" + port + druidPattern);
        //XAdmin系统访问地址
        log.warn("x-admin: http://localhost:" + port );

    }
}
