package com.kj.kdove.usermatching.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Value("${RedisDBIndex.matchingRedisDBIndex}")
    private int matchingRedisDBIndex;

    @Value("${RedisDBIndex.smsCodeRedisDBIndex}")
    private int smsCodeRedisDBIndex;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
    public JedisPoolConfig getRedisConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    @Scope("prototype") //多例模式，不然无法修改redis db
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean(name = "matchingRedis")
    public RedisTemplate<?,?> getMatchingRedisTemplate(){
        JedisConnectionFactory factory = getConnectionFactory();
        final RedisTemplate<?,?> template  = new StringRedisTemplate();
        factory.setDatabase(matchingRedisDBIndex);
        template.setConnectionFactory(factory);
        //设置序列化Key的实例化对象
        template.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
    @Bean(name = "smsCodeRedis")
    public RedisTemplate<?,?> getSmsCodeRedisTemplate(){
        JedisConnectionFactory factory = getConnectionFactory();
        final RedisTemplate<?,?> template  = new StringRedisTemplate();
        factory.setDatabase(smsCodeRedisDBIndex);
        template.setConnectionFactory(factory);
        //设置序列化Key的实例化对象
        template.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }


}
