package com.kj.kdove.dbservice.controller;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.matching.UserEnum;
import com.kj.kdove.dbservice.service.api.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/us/data")
public class UserInfoProviderController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 通过username查询用户信息
     * @param username
     * @return
     */
    @GetMapping(value = "/getuser/{username}")
    public ResponseData<KDoveUser> getUserByUserName(@PathVariable String username){

        KDoveUser userByUserName = userInfoService.getUserByUserName(username);
        if (userByUserName != null){
            return new ResponseData<>(
                    UserEnum.SELECT_USER_BY_USERNAME_SUCCESS.getCode(),
                    UserEnum.SELECT_USER_BY_USERNAME_SUCCESS.getMessage(),
                    userByUserName
            );
        }
        return new ResponseData<>(
                UserEnum.SELECT_USER_BY_USERNAME_FALSE.getCode(),
                UserEnum.SELECT_USER_BY_USERNAME_FALSE.getMessage(),
                null
        );
    }

    /**
     * 注册用户
     * @param kDoveUser
     * @return
     */
    @PostMapping(value = "/adduser/reg")
    public Map<String,Integer> addUser(@RequestBody KDoveUser kDoveUser){
        Map<String,Integer> resultMap = new HashMap<>();
        Integer state = userInfoService.addUser(kDoveUser);
        resultMap.put("state",state);
        return resultMap;
    }

}
