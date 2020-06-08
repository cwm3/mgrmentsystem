package org.cwm3.mgrsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "org.cwm3.mgrsystem.mapper")
@EnableScheduling
public class mgrsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(mgrsystemApplication.class, args);
    }

}
