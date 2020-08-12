package com.kj.kdove.usermatching;

import com.kj.kdove.usermatching.service.api.SmsRDBService;
import com.kj.kdove.usermatching.sms.AsyncTaskSms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmsRedisTest {

    @Autowired
    private SmsRDBService smsRDBService;

    @Autowired
    private AsyncTaskSms asyncTaskSms;


    @Test
    public void test1(){
        smsRDBService.addSmsCodetoRDB("15884291257","135656");
    }
    @Test
    public void test2(){
        List<String> smsCodeFromRDBbyPhoneNum = smsRDBService.getSmsCodeFromRDBbyPhoneNum("15884291257");
        for (String sms:smsCodeFromRDBbyPhoneNum
             ) {
            System.out.println(sms);
        }
    }

    @Test
    public void test3(){
        asyncTaskSms.RedisSms("15884291257","826548");
    }
}
