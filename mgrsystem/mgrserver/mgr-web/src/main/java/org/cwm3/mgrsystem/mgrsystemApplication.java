package org.cwm3.mgrsystem;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "org.cwm3.mgrsystem.mapper")
@EnableScheduling
public class mgrsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(mgrsystemApplication.class, args);
    }
//    @Bean
//    public PageHelper pageHelper() {
//        System.out.println("MyBatisConfiguration.pageHelper()");
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }

}
