package com.kj.kdove.usermatching.redis;

import com.google.common.collect.Lists;
import com.kj.kdove.usermatching.redis.api.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    @Qualifier("matchingRedis")
    private RedisTemplate<String,?> redisTemplate_matching;

    @Autowired
    @Qualifier("smsCodeRedis")
    private RedisTemplate<String,?> redisTemplate_sms;



    @Override
    public boolean set_matching(String key, String value) {
        Boolean result  = redisTemplate_matching.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate_matching.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean set_sms(String key, String value) {
        Boolean result  = redisTemplate_sms.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate_sms.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String get_matching(String key) {
        String result = redisTemplate_matching.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate_matching.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public List<String> get_sms(String key) {
        List<String> smsArray = Lists.newArrayList();
        Set<String> keys = redisTemplate_sms.keys(key + "*");
        for (String phone_key:keys
        ) {
            String result = redisTemplate_sms.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate_sms.getStringSerializer();
                    byte[] value = connection.get(serializer.serialize(phone_key));
                    return serializer.deserialize(value);
                }
            });
            smsArray.add(result);
        }
        return smsArray;
    }

    @Override
    public boolean expire_matching(String key, long expire) {
        return redisTemplate_matching.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean expire_sms(String key, long expire) {
        return redisTemplate_sms.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove_matching(String key) {
        boolean result = redisTemplate_matching.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate_matching.getStringSerializer();
                connection.del(key.getBytes());
                return true;
            }
        });
        return result;
    }

    @Override
    public Set<String> getRedisKeys_matching() {

        List<String> keysList = new ArrayList<>();

        Set<String> keys = redisTemplate_matching.keys("*");

        return keys;
    }
}

