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
    public boolean addUserIdtoRDB(String userId,String ipAddr) {

        String value = redisService.get_matching(userId);
        if (value == null){

            redisService.set_matching(userId,userId+"/"+ipAddr+"-");
//            redisService.expire(userId,100);
            return true;
        }else {
            return false;
        }
    }


    @Override
    public String getValueByUserIdFromRDB(String userId) {
        return redisService.get_matching(userId);
    }

    @Override
    public boolean removeUserIdFromRDB(String userId) {
        return redisService.remove_matching(userId);
    }

    @Override
    public boolean resetUserIdFromRDB(String key, String value) {
        String s = redisService.get_matching(key);
        if (s == null){
            return false;
        }else {
            redisService.set_matching(key,value);
            return true;
        }
    }

    @Override
    public List<String> getAllKeyExcuteUserId(String userId) {
        List<String> keys = new ArrayList<>();
        Set<String> redisKeys = redisService.getRedisKeys_matching();
        for (String key:redisKeys
             ) {
            if (!key.equals(userId)){
                keys.add(key);
            }
        }
        return keys;
    }
}
