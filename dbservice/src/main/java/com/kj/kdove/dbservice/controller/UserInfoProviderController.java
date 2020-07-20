package com.kj.kdove.dbservice.controller;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.dbservice.service.api.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/us/data")
public class UserInfoProviderController {

    @Autowired
    private UserInfoService userInfoService;


    @GetMapping(value = "/getuser/{username}")
    public KDoveUser getUserByUserName(@PathVariable String username){
        System.out.println(username);
        KDoveUser userByUserName = userInfoService.getUserByUserName(username);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return userByUserName;
    }

    @PostMapping(value = "/userinfo/reg")
    public int addUser(@RequestBody KDoveUser kDoveUser){
        return userInfoService.addUser(kDoveUser);
    }
}
