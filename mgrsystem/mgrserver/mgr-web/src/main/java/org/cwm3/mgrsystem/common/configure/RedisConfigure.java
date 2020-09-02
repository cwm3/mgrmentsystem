package org.cwm3.mgrsystem.common.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * @author mrcwm
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfigure extends CachingConfigurerSupport {

    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    @Bean
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

//        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用 jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的 value序列化方式采用 jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public KeyGenerator myKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                //        Logger log = Logger.getLogger("Keygenerate");
                log.info("Keygenerate");
                StringBuilder key = new StringBuilder();
                key.append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append(":");  //先将类的全限定名和方法名拼装在 key 中
                if (params.length == 0) {
                    return key.append(NO_PARAM_KEY).toString();
                }
                for (Object param : params) {   //通过遍历参数,将参数也拼装在 key 中,保证每次获取key 的唯一性
                    if (param == null) {
                        key.append(NULL_PARAM_KEY);
                    } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                        int length = Array.getLength(param);
                        for (int i = 0; i < length; i++) {
                            key.append(Array.get(param, i));
                            key.append(',');
                        }
                    } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                        key.append(param);
                    } else {
                        key.append(param.hashCode());   //如果是map 或 model 类型
                    }
                    key.append('-');
                }
                return key.toString();
            }
        };
    }

}
