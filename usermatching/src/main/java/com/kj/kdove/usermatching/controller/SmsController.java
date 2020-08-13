package com.kj.kdove.usermatching.controller;
import com.google.common.collect.Maps;
import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.dto.SmsDto;
import com.kj.kdove.commons.enums.UserEnum;
import com.kj.kdove.usermatching.service.api.SmsRDBService;
import com.kj.kdove.usermatching.service.api.UcodeRDBService;
import com.kj.kdove.usermatching.service.api.UserDBService;
import com.kj.kdove.usermatching.sms.AsyncTaskSms;
import com.kj.kdove.usermatching.sms.SendSms;
import com.kj.kdove.usermatching.utils.GsonUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "reg")
public class SmsController {

    @Autowired
    private AsyncTaskSms asyncTaskSms;

    @Autowired
    private SmsRDBService smsRDBService;

    @Autowired
    private UserDBService userDBService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    private UcodeRDBService ucodeRDBService;

    /**
     * 短息接口，判断状态码（状态码由第三方提供）
     * 200：发送成功
     * 203：第三方服务端异常
     * 206：手机号码错误
     * else：超过每日最大发送次数
     * catch：抛网络异常
     * 此上为第三方code，不是系统的code，系统返回状态码由UserEnum提供
     *
     * 调用接口直接捕获用户信息
     * redis：临时保存phone：code （10min）
     * mysql：保存phone，自动生成ucode
     * @param phoneNumber
     * @return
     */

    @CrossOrigin(origins = "*")
    @GetMapping("/sms/getsms")
    public ResponseData<Map<String,String>> registSms(@RequestParam String phoneNumber){
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

    /**
     * 验证码校验接口
     * 返回用户数据
     * redis：保存用户状态
     * 使用上一步生成的ucode和phone后台生成xcode
     * redis: xcode:new Date()
     * @param smsDto
     * @return
     */

    @CrossOrigin(origins = "*")
    @PostMapping("/sms/verification")
    public ResponseData<Map<String,String>> smsVerification(@RequestBody SmsDto smsDto){
        List<String> smsCodeFromRDBbyPhoneNum = smsRDBService.getSmsCodeFromRDBbyPhoneNum(smsDto.getPhoneNumber());
        //验证码正确
        if (smsCodeFromRDBbyPhoneNum.contains(smsDto.getSmsCode())) {
            ResponseData<KDoveUser> userByUserName = userDBService.getUserByUserName(smsDto.getPhoneNumber());
            //封装返回参数
            HashMap<String, String> respMap = Maps.newHashMap();
            KDoveUser data = userByUserName.getData();
            respMap.put("username",data.getUsername());
            respMap.put("nickname",data.getNickname());
            respMap.put("ucode",data.getUcode());
            respMap.put("headurl",data.getHeadurl());
            respMap.put("regdate",simpleDateFormat.format(data.getRegdate()));
            respMap.put("email",data.getEmail());
            respMap.put("id",String.valueOf(data.getId()));
            //保存用户状态，后台生成xcode，保存redis，有效期6天
            ucodeRDBService.addUcodetoRDB(data.getUcode(),data.getUsername(),new Date());
            //返回参数
            return new ResponseData<>(
                    userByUserName.getCode(),
                    userByUserName.getMessage(),
                    respMap
            );
        }else {
            //验证码错误
            return new ResponseData<>(
                    UserEnum.SMS_ERRO.getCode(),
                    UserEnum.SMS_ERRO.getMessage(),
                    null
            );
        }
    }
}