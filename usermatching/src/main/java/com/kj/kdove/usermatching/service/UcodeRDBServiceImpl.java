package com.kj.kdove.usermatching.service;

import com.google.common.collect.Maps;
import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.utils.XcodeUtils;
import com.kj.kdove.usermatching.redis.api.RedisService;
import com.kj.kdove.usermatching.service.api.UcodeRDBService;

import com.kj.kdove.usermatching.service.api.UserDBService;
import com.kj.kdove.usermatching.utils.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Service
public class UcodeRDBServiceImpl implements UcodeRDBService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    private UserDBService userDBService;

    @Override
    public void addUcodetoRDB(String ucode, String phoneNum, Date date) {
        String xcode = XcodeUtils.getXcode(ucode, phoneNum);
        String flagXcode = redisService.get_ucode(xcode);
        if (flagXcode == null || "".equals(flagXcode)){
            Map<String,String> map = Maps.newHashMap();
            ResponseData<KDoveUser> userByUserName = userDBService.getUserByUserName(phoneNum);
            KDoveUser data = userByUserName.getData();
            map.put("date",simpleDateFormat.format(date));
            map.put("username",data.getUsername());
            map.put("nickname",data.getNickname());
            map.put("ucode",data.getUcode());
            map.put("headurl",data.getHeadurl());
            map.put("email",data.getEmail());
            map.put("id",data.getId().toString());
            map.put("regdate",simpleDateFormat.format(data.getRegdate()));
            redisService.set_ucode(xcode, GsonUtil.GsonString(map));
            redisService.expire_ucode(xcode,60*60*24*6);
        }
    }

    @Override
    public String getValueFromRDBbyUcodeX(String Xcode) {
        return redisService.get_ucode(Xcode);
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
