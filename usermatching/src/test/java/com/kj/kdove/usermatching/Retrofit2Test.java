package com.kj.kdove.usermatching;

import com.kj.kdove.usermatching.retrofit2.DbApi;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Retrofit2Test {

    @Autowired
    private DbApi dbApi;

//    @Test
//    public void test1(){
//        Call<ResponseBody> userinfo = dbApi.getUserinfo();
//        System.out.println(userinfo);
//    }
}
