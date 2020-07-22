package org.cwm3.mgrsystem;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "org.cwm3.mgrsystem.mapper")
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class mgrsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(mgrsystemApplication.class, args);
    }

}
