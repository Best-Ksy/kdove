package com.kj.kdove.usermatching.service;

import com.kj.kdove.usermatching.redis.api.RedisService;
import com.kj.kdove.usermatching.service.api.SmsRDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsRDBServiceImpl implements SmsRDBService {


    @Autowired
    private RedisService redisService;


    @Override
    public void addSmsCodetoRDB(String phoneNum, String smsCode) {
        String key = phoneNum+"-"+System.currentTimeMillis();
        redisService.set_sms(key,smsCode);
        //验证码设置五分钟有效时间
//        expireSmsCode(key,60*10);
    }

    @Override
    public List<String> getSmsCodeFromRDBbyPhoneNum(String phoneNum) {
        return  redisService.get_sms(phoneNum);
    }

    @Override
    public void expireSmsCode(String key, long expire) {
        redisService.expire_sms(key,expire);
    }
}
