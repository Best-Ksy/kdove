package com.kj.kdove.usermatching.service.api;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;

/**
 * 数据库 service （调用dbservice相关api）
 */

public interface UserDBService {

    /**
     * 通过username返回查询结果
     * 没有这个用户和有这个用户返回原生的返回结果
     * 网络错误，dbservice没有启动，返回code=204
     * @param username
     * @return
     */
    ResponseData<KDoveUser> getUserByUserName(String username);

    Boolean addUser(KDoveUser kDoveUser);



}
