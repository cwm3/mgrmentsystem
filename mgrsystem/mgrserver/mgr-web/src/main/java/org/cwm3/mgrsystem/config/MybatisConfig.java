package org.cwm3.mgrsystem.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.github.pagehelper.PageHelper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * @description:
 * @author: chengweiming
 * @time: 2020/7/1 12:43
 */
@org.springframework.context.annotation.Configuration
public class MybatisConfig {


//    /**
//     * 分页插件
//     * @return
//     */
//    @Bean
//    public PageHelper pageHelper() {
//        System.out.println("MybatisConfiguration.pageHelper()");
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }
}