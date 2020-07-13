package com.kj.kdove.usermatching.service;

import com.kj.kdove.commons.matching.MatchingEnum;
import com.kj.kdove.usermatching.service.api.MatchingService;
import com.kj.kdove.usermatching.service.api.UserRDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchingServiceImpl implements MatchingService {


    @Autowired
    private UserRDBService userRDBService;

    @Autowired
    private Random random;

    @Override
    public Map<String,String> MatchingUser(String userId) {
        Map<String,String> remap = new Hashtable<>();
        int count = 0;
        while (count < 5){
            count++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = userRDBService.getValueByUserIdFromRDB(userId);
            String[] split = value.split("-");
            //检测是否匹配
            if (split.length == 1){
                String randomKey = randomKey(userId,0);
                if (!randomKey.equals(MatchingEnum.MATCHING_OVERTIME.getMessage())
                        && !randomKey.equals(MatchingEnum.MATCHING_NUM_EXC.getMessage())){
                    if (flageKey(userId,randomKey)){
                        userRDBService.resetUserIdFromRDB(userId,userId+"-"+randomKey);
                        userRDBService.resetUserIdFromRDB(randomKey,randomKey+"-"+userId);
                        remap.put(userId,randomKey);
                        break;
                    }else {
                        continue;
                    }
                }
            }else {
                remap.put(split[0],split[1]);
                break;
            }
        }
        return remap;
    }

    public String randomKey(String userId,int count){
        if (count == 2){
            return MatchingEnum.MATCHING_OVERTIME.getMessage();
        }
        //如果没有匹配的话，首先获取redis中所有的key
        List<String> redisKeys = userRDBService.getAllKeyExcuteUserId(userId);
        int size = redisKeys.size();
        //产生随机数,获取随机到的key
        if (size == 0){
            return MatchingEnum.MATCHING_NUM_EXC.getMessage();
        }
        int randomKeyIndex = random.nextInt(size);
        String Key_str = redisKeys.get(randomKeyIndex);
        String valueOfKey = userRDBService.getValueByUserIdFromRDB(Key_str);
        //判断得到的这个随机key现在是否被其他用户绑定
        if (valueOfKey.split("-").length == 1){
            return Key_str;
        }else {
            //如果这个key已经被绑定的话，重新开始随机匹配key
            return randomKey(userId,count++);
        }
    }

    /**
     * 最终检测是否都没有绑定
     * @param key1
     * @param key2
     * @return
     */
    public boolean flageKey(String key1,String key2){
        String k1_value = userRDBService.getValueByUserIdFromRDB(key1);
        String k2_value = userRDBService.getValueByUserIdFromRDB(key2);
        return k1_value.split("-").length == 1 && k2_value.split("-").length == 1;
    }
}
