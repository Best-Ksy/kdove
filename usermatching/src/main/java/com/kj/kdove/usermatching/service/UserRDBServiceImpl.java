package com.kj.kdove.usermatching.service;

import com.kj.kdove.usermatching.redis.api.RedisService;
import com.kj.kdove.usermatching.service.api.UserRDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service()
public class UserRDBServiceImpl implements UserRDBService {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean addUserIdtoRDB(String userId) {
        String value = redisService.get(userId);
        if (value == null){
            redisService.set(userId,userId+"-");
//            redisService.expire(userId,100);9
            return true;
        }else {
            return false;
        }
    }


    @Override
    public String getValueByUserIdFromRDB(String userId) {
        return redisService.get(userId);
    }

    @Override
    public boolean removeUserIdFromRDB(String userId) {
        return redisService.remove(userId);
    }

    @Override
    public boolean resetUserIdFromRDB(String key, String value) {
        String s = redisService.get(key);
        if (s == null){
            return false;
        }else {
            redisService.set(key,value);
            return true;
        }
    }

    @Override
    public List<String> getAllKeyExcuteUserId(String userId) {
        List<String> keys = new ArrayList<>();
        Set<String> redisKeys = redisService.getRedisKeys();
        for (String key:redisKeys
             ) {
            if (!key.equals(userId)){
                keys.add(key);
            }
        }
        return keys;
    }
}
