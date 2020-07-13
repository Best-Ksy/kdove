package com.kj.kdove.usermatching.service.api;

import java.util.List;

public interface UserRDBService {


    /**
     *将用户的id放入redis
     * 如果用户id已存在，返回false
     * 如果用户id不存在，返回true
     * @param userId
     * @return
     */
    boolean addUserIdtoRDB(String userId,String ipAddr);

    /**
     * 通过用户id获取redis中对应的value
     * @param userId
     * @return
     */
    String getValueByUserIdFromRDB(String userId);

    /**
     * 移出redis中的用户
     * @param userId
     * @return
     */
    boolean removeUserIdFromRDB(String userId);


    /**
     * 修改value
     * @param key
     * @param value
     * @return
     */
    boolean resetUserIdFromRDB(String key,String value);

    /**
     * 获取所有的key
     * @param userId
     * @return
     */
    List<String> getAllKeyExcuteUserId(String userId);





}
