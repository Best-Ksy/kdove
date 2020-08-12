package com.kj.kdove.usermatching.service;

import com.kj.kdove.usermatching.redis.api.RedisService;
import com.kj.kdove.usermatching.service.api.UcodeRDBService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UcodeRDBServiceImpl implements UcodeRDBService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Override
    public void addUcodetoRDB(String ucode, String phoneNum, Date date) {
        String xcode = getXcode(ucode, phoneNum);

    }

    @Override
    public String getValueFromRDBbyUcodeX(String ucode, String phoneNum) {
        return null;
    }

    @Override
    public void expireUCode(String ucode, String phoneNum, long time) {

    }

    @Override
    public void delUserState(String ucode, String phoneNum) {

    }

    public String getXcode(String ud,String ph){
        //这里需要加密，对称加密，前端无法拿到这个xcode，xcode全是后端计算
        // 前端只需要提供后端传的ucode和phonenum
        char[] chars = ph.toCharArray();
        return null;
    }
}
