package com.kj.kdove.usermatching.sms;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.enums.UserEnum;
import com.kj.kdove.usermatching.service.api.SmsRDBService;
import com.kj.kdove.usermatching.service.api.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@Async("taskExecutor")
public class AsyncTaskSms {

    @Autowired
    private SmsRDBService smsRDBService;

    @Autowired
    private UserDBService userDBService;

    //1.验证码发送成功，手机号-验证码 存入redis
    //2.手机号码存入mysql
    public void RedisSms(String phoneNum,String smsCode){

        //缓存验证码
        smsRDBService.addSmsCodetoRDB(phoneNum,smsCode);

        //保存新用户手机号（用户名）
        ResponseData<KDoveUser> userByUserName = userDBService.getUserByUserName(phoneNum);
        if (userByUserName.getCode() == UserEnum.SELECT_USER_BY_USERNAME_FALSE.getCode()){
            //数据库中不存在此用户
            userDBService.addUser(new KDoveUser(phoneNum));
        }
    }

//    //手机号码不正确，写入日志文件
//    public void sendSmsMobileErrorLog(String phoneNum,String smsCode){
//
//    }



}
