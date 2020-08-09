package com.kj.kdove.usermatching.controller;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.enums.UserEnum;
import com.kj.kdove.usermatching.sms.SendSms;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "reg")
public class SmsController {


    @CrossOrigin(origins = "*")
    @GetMapping("getsms")
    public ResponseData<Map<String,String>> registSms(String phoneNumber){
        Map <String, String> map = null;
        try {
            map = SendSms.sendCode(phoneNumber);
            Integer smsResultInt = Integer.valueOf(map.get("smsResultCode").split("\\.")[0]);
            if (smsResultInt == 200){
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
            }else if (smsResultInt == 206){
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