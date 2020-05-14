package com.example.questem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Linmo
 * @create 2020/5/14 20:37
 */
@Configuration
public class RedisConfiguration {

    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();
        objectObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer KeySerializer = new StringRedisSerializer();
        objectObjectRedisTemplate.setHashKeySerializer(KeySerializer);
        objectObjectRedisTemplate.setKeySerializer(KeySerializer);
        objectObjectRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return objectObjectRedisTemplate;
    }
}
