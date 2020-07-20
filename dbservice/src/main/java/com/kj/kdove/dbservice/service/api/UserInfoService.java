package com.kj.kdove.dbservice.service.api;

import com.kj.kdove.commons.domain.KDoveUser;

import java.util.List;

public interface UserInfoService {

    /**
     * 添加用户
     * @param kDoveUser
     * @return
     */
    Integer addUser(KDoveUser kDoveUser);


    /**
     *
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    KDoveUser getUserByUserName(String userName);


}
