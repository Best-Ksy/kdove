package com.kj.kdove.usermatching.redis.api;


import java.util.List;
import java.util.Set;

public interface RedisService {

    /**
     * set存数据(matching)
     * @param key
     * @param value
     * @return
     */
    boolean set_matching(String key, String value);

    /**
     * set存数据(sms)
     * @param key
     * @param value
     * @return
     */
    boolean set_sms(String key, String value);

    /**
     * get获取数据(matching)
     * @param key
     * @return
     */
    String get_matching(String key);

    /**
     * get获取数据(sms)
     * @param key
     * @return
     */
    List<String> get_sms(String key);

    /**
     * 设置有效天数(matching)
     * @param key
     * @param expire
     * @return
     */
    boolean expire_matching(String key, long expire);

    /**
     * 设置有效天数(sms)
     * @param key
     * @param expire
     * @return
     */
    boolean expire_sms(String key, long expire);

    /**
     * 移除数据(matching)
     * @param key
     * @return
     */
    boolean remove_matching(String key);

    /**
     * 获取所有的key(matching)
     * @return
     */
    Set<String> getRedisKeys_matching();


}
