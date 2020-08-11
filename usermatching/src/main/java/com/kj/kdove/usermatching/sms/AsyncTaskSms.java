package com.kj.kdove.usermatching.sms;

import com.kj.kdove.usermatching.service.api.UserRDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@Async("taskExecutor")
public class AsyncTaskSms {
//
//    @Value("${logfile.path}")
//    private String logPath;



    //验证码发送成功，手机号-验证码 存入redis
    public void RedisSms(String phoneNum,String smsCode){
//        userRDBService.addUserIdtoRDB()
        System.out.println(Thread.currentThread().getName()+"  "+ phoneNum+"  "+ smsCode);
    }

    //手机号码不正确，写入日志文件
    public void sendSmsMobileErrorLog(String phoneNum,String smsCode){

    }



}
