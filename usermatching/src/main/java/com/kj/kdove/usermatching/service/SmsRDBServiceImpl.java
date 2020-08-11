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
        redisService.set_sms(phoneNum+"-"+System.currentTimeMillis(),smsCode);
    }

    @Override
    public List<String> getSmsCodeFromRDBbyPhoneNum(String phoneNum) {
        List<String> sms = redisService.get_sms(phoneNum);
        for (String s:sms
             ) {
            System.out.println(s);
        }
        return null;
    }

    @Override
    public void expireSmsCode(String key, long expire) {

    }
}
