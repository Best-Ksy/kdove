package com.kj.kdove.usermatching.controller;


import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.usermatching.retrofit2.DbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

@RestController
public class Retrofit2test {


    @Autowired
    private DbApi dbApi;

    @GetMapping("/test1/{username}")
    public KDoveUser test1(@PathVariable String username){
        Call<KDoveUser> userinfo = dbApi.getUserinfo(username);
        userinfo.enqueue(new Callback<KDoveUser>() {
            @Override
            public void onResponse(Call<KDoveUser> call, Response<KDoveUser> response) {

                System.out.println(response.body());

            }
            @Override
            public void onFailure(Call<KDoveUser> call, Throwable throwable) {
                System.out.println("请求失败");
            }
        });
        return new KDoveUser();
    }

}
