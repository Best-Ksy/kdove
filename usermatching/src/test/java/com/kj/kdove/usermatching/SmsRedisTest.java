package com.kj.kdove.usermatching;

import com.kj.kdove.usermatching.service.api.SmsRDBService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmsRedisTest {

    @Autowired
    private SmsRDBService smsRDBService;


    @Test
    public void test1(){
        smsRDBService.addSmsCodetoRDB("15884291257","565656");
    }
    @Test
    public void test2(){
        smsRDBService.getSmsCodeFromRDBbyPhoneNum("15884291257");
    }
}
