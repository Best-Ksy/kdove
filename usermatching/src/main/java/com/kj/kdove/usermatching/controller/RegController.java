package com.kj.kdove.usermatching.controller;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.enums.UserEnum;
import com.kj.kdove.usermatching.service.api.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/profile")
public class RegController {

    @Autowired
    private UserDBService userDBService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @PostMapping(value = "/us/reg")
    public ResponseData<Map<String,String>> userReg(@RequestBody KDoveUser kDoveUser){

        Map<String,String> responseMap = new HashMap<>();
        ResponseData<KDoveUser> responseData = userDBService.getUserByUserName(kDoveUser.getUsername());


        //用户名存在
        if (responseData.getCode() == UserEnum.SELECT_USER_BY_USERNAME_SUCCESS.getCode()){
            responseMap.put("username",kDoveUser.getUsername());
            responseMap.put("date",simpleDateFormat.format(new Date()));
            return new ResponseData<>(
                    UserEnum.USER_REG_FALSE_NO1.getCode(),
                    UserEnum.USER_REG_FALSE_NO1.getMessage(),
                    responseMap
            );
        }
        //用户名参数为null
        if (responseData.getCode() == UserEnum.SELECT_USER_BY_USERNAME_PARAM_ERRO.getCode()){
            responseMap.put("username",kDoveUser.getUsername());
            responseMap.put("date",simpleDateFormat.format(new Date()));
            return new ResponseData<>(
                    UserEnum.USER_REG_FALSE_NO2.getCode(),
                    UserEnum.USER_REG_FALSE_NO2.getMessage(),
                    responseMap
            );
        }
        //dbservice网络错误
        if (responseData.getCode() == UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getCode()){
            responseMap.put("username",kDoveUser.getUsername());
            responseMap.put("date",simpleDateFormat.format(new Date()));
            return new ResponseData<>(
                    UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getCode(),
                    UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getMessage(),
                    responseMap
            );
        }

        //用户名不存在
        Boolean aBoolean = userDBService.addUser(kDoveUser);
        if (aBoolean) {
            ResponseData<KDoveUser> userByUserName = userDBService.getUserByUserName(kDoveUser.getUsername());
            if (userByUserName.getCode() == UserEnum.SELECT_USER_BY_USERNAME_SUCCESS.getCode()){
                KDoveUser data = userByUserName.getData();
                responseMap.put("username",data.getUsername());
                responseMap.put("nickname",data.getNickname());
                responseMap.put("ucode",data.getUcode());
                responseMap.put("email",data.getEmail());
                responseMap.put("headurl",data.getHeadurl());
                responseMap.put("regdate",simpleDateFormat.format(data.getRegdate()));
                return new ResponseData<>(
                        UserEnum.USER_REG_SUCCESS.getCode(),
                        UserEnum.USER_REG_SUCCESS.getMessage(),
                        responseMap
                );
            }else {
                return new ResponseData<>(
                       UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getCode(),
                       UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getMessage(),
                       null
                );
            }
        }
        return new ResponseData<>(
                UserEnum.USER_REG_FALSE.getCode(),
                UserEnum.USER_REG_FALSE.getMessage(),
                null
        );
    }




}
