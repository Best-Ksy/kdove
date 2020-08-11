package com.kj.kdove.usermatching.service.api;

import java.util.List;

public interface SmsRDBService {


    /**
     * 添加验证码
     * @param phoneNum
     * @param smsCode
     * @return
     */
    void addSmsCodetoRDB(String phoneNum,String smsCode);

    /**
     * 查询验证码
     * @param phoneNum
     * @return
     */
    List<String> getSmsCodeFromRDBbyPhoneNum(String phoneNum);

    /**
     * 设置验证码有效时间
     * @param key
     * @param expire
     */
    void expireSmsCode(String key, long expire);
}
