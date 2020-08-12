package com.kj.kdove.usermatching;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.usermatching.retrofit2.DbApi;
import com.kj.kdove.usermatching.service.api.UserDBService;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Retrofit2Test {

    @Autowired
    private DbApi dbApi;

    @Autowired
    private UserDBService userDBService;

    @Test
    public void test1(){
        Call<ResponseData<KDoveUser>> userinfo = dbApi.getUserinfo("kjsda");
        System.out.println(userinfo);
    }

    @Test
    public void test2(){
        ResponseData<KDoveUser> dasdsadaa = userDBService.getUserByUserName("1588");
        System.out.println(dasdsadaa.toString());
    }
}
