package com.kj.kdove.usermatching.controller;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.enums.UserEnum;
import com.kj.kdove.usermatching.sms.AsyncTaskSms;
import com.kj.kdove.usermatching.sms.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "reg")
public class SmsController {

    @Autowired
    private AsyncTaskSms asyncTaskSms;

    /**
     * 短息接口，判断状态码（状态码由第三方提供）
     * 200：发送成功
     * 203：第三方服务端异常
     * 206：手机号码错误
     * else：超过每日最大发送次数
     * catch：抛网络异常
     * 此上为第三方code，不是系统的code，系统返回状态码由UserEnum提供
     * @param phoneNumber
     * @return
     */

    @CrossOrigin(origins = "*")
    @GetMapping("getsms")
    public ResponseData<Map<String,String>> registSms(String phoneNumber){
        Map <String, String> map = null;
        try {
            map = SendSms.sendCode(phoneNumber);
            Integer smsResultInt = Integer.valueOf(map.get("smsResultCode").split("\\.")[0]);
            if (smsResultInt == 200){
                //验证码成功发送
                //验证码存入redis,新手机号存入数据库（异步执行）
                asyncTaskSms.RedisSms(map.get("phonenumber"),map.get("smscode"));
                return new ResponseData<Map<String,String>>(
                        UserEnum.SEND_SMS_SUCCESS.getCode(),
                        UserEnum.SEND_SMS_SUCCESS.getMessage(),
                        map
                );
            }else if (smsResultInt == 203){
                map.remove("smscode");
                return new ResponseData<Map<String,String>>(
                        UserEnum.SEND_SMS_SERVICE_EXCEPTION.getCode(),
                        UserEnum.SEND_SMS_SERVICE_EXCEPTION.getMessage(),
                        map
                );
            }else if (smsResultInt == 405){
                map.remove("smscode");
                return new ResponseData<Map<String,String>>(
                        UserEnum.SEND_SMS_MOBILE_ERROR.getCode(),
                        UserEnum.SEND_SMS_MOBILE_ERROR.getMessage(),
                        map
                );
            }else {
                map.remove("smscode");
                return new ResponseData<Map<String,String>>(
                        UserEnum.SEND_SMS_OVER_MAXNUM_LIMITS.getCode(),
                        UserEnum.SEND_SMS_OVER_MAXNUM_LIMITS.getMessage(),
                        map
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.remove("smscode");
            return new ResponseData<Map<String,String>>(
                    UserEnum.SEND_SMS_EXC.getCode(),
                    UserEnum.SEND_SMS_EXC.getMessage(),
                    map
            );
        }
    }
}