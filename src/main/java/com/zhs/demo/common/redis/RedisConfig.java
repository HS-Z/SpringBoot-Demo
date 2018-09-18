package com.zhs.demo.common.redis;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.Charset;


@Component
@Configuration
public class RedisConfig implements Serializable {


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        return connectionFactory;
    }



    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }



    @Bean
    protected RedisUtils redisUtils(@SuppressWarnings("rawtypes") RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<String> serializer = new RedisSerializer<String>() {
            private JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
            private Charset charset = Charset.forName("UTF-8");

            @Override
            public String deserialize(byte[] buf) {
                if (buf == null)
                    return null;
                if (buf.length > 4) {
                    if (readShort(buf, 0) == ObjectStreamConstants.STREAM_MAGIC) {
                        buf = (byte[]) this.jdkSerializer.deserialize(buf);
                    }
                }
                return new String(buf, charset).intern();
            }

            private int readShort(byte[] buf, int i) {
                return (short) ((buf[i] & 0xff) << 8 | buf[i + 1] & 0xff);
            }

            @Override
            public byte[] serialize(String value) {
                return value == null ? null : value.getBytes(charset);
            }
        };
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return new RedisUtils(redisTemplate);
    }

}