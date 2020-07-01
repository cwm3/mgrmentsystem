//package org.cwm3.mgrsystem.config;//package org.cwm3.mgrsystem.config;
//
//import com.github.pagehelper.PageHelper;
//import org.apache.ibatis.session.Configuration;
//import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
//import org.springframework.context.annotation.Bean;
//import java.util.Properties;
//
//
///**
// * @description:
// * @author: chengweiming
// * @time: 2020/7/1 12:43
// */
//@org.springframework.context.annotation.Configuration
//public class MybatisConfig {
//
//    /**
//     * 目的防止驼峰命名规则
//     * @return
//     */
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer(){
//        return new ConfigurationCustomizer(){
//
//            @Override
//            public void customize(Configuration configuration) {
//                configuration.setMapUnderscoreToCamelCase(true);
//            }
//        };
//    }
//
////    /**
////     * 分页插件
////     * @return
////     */
////    @Bean
////    public PageHelper pageHelper() {
////        System.out.println("MybatisConfiguration.pageHelper()");
////        PageHelper pageHelper = new PageHelper();
////        Properties p = new Properties();
////        p.setProperty("offsetAsPageNum", "true");
////        p.setProperty("rowBoundsWithCount", "true");
////        p.setProperty("reasonable", "true");
////        pageHelper.setProperties(p);
////        return pageHelper;
////    }
//}