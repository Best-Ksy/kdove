package com.kj.kdove.usermatching;

import com.kj.kdove.commons.utils.XcodeUtils;
import com.kj.kdove.usermatching.service.api.UcodeRDBService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XcodeTest {

    @Autowired
    private UcodeRDBService ucodeRDBService;

    @Test
    public void test1(){
//        String ph = "15884291257";
//        String substring = ph.substring(0,4);
//        String substring1 = ph.substring(ph.length() - 4, ph.length());
//        System.out.println(substring);
//        System.out.println(substring1);
        String xcode = XcodeUtils.getXcode("256341", "15884291257");
        System.out.println(xcode);
//
//        String code = "1234567";
//        System.out.println(code.substring(code.length()/2,code.length()));
//        System.out.println(code.substring(0,code.length()/2));
    }

    @Test
    public void test2(){
        ucodeRDBService.addUcodetoRDB("256341","15884291257",new Date());
    }


}
