package com.kj.kdove.usermatching.service.api;

import java.util.Date;

public interface UcodeRDBService {

    /**
     * 添加加密ucode到redis，保存用户状态
     * @param ucode
     * @param phoneNum
     * @param date
     */
    void addUcodetoRDB(String ucode,String phoneNum, Date date);

    /**
     * 通过加密ucode获取用户状态值
     * @param ucode
     * @param phoneNum
     * @return
     */
    String getValueFromRDBbyUcodeX(String Xcode);

    /**
     * 用户状态有效期
     * @param ucode
     * @param phoneNum
     * @param time
     */
    void expireUCode(String ucode,String phoneNum,long time);

    /**
     * 删除用户状态
     * @param ucode
     * @param phoneNum
     */
    void delUserState(String ucode,String phoneNum);
}
