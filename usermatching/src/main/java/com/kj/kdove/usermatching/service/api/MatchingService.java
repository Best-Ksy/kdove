package com.kj.kdove.usermatching.service.api;

import java.util.Map;


/**
 * 匹配逻辑 service
 */

public interface MatchingService {

    /**
     * 匹配
     * @param userId
     */
    public Map<String,String> MatchingUser(String userId);

}
