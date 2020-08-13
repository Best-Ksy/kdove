package com.kj.kdove.usermatching.service;

import com.kj.kdove.commons.utils.XcodeUtils;
import com.kj.kdove.usermatching.redis.api.RedisService;
import com.kj.kdove.usermatching.service.api.UcodeRDBService;

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
        String xcode = XcodeUtils.getXcode(ucode, phoneNum);
        String flagXcode = redisService.get_ucode(xcode);
        if (flagXcode == null || "".equals(flagXcode)){
            redisService.set_ucode(xcode, simpleDateFormat.format(date));
            redisService.expire_ucode(xcode,60*60*24*6);
        }
    }

    @Override
    public String getValueFromRDBbyUcodeX(String ucode, String phoneNum) {
        return redisService.get_ucode(XcodeUtils.getXcode(ucode,phoneNum));
    }

    @Override
    public void expireUCode(String ucode, String phoneNum, long time) {
        redisService.expire_ucode(XcodeUtils.getXcode(ucode,phoneNum),time);
    }

    @Override
    public void delUserState(String ucode, String phoneNum) {
        redisService.remove_ucode(XcodeUtils.getXcode(ucode,phoneNum));
    }


}
