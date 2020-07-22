//package com.kj.kdove.usermatching.controller;
//
//
//import com.kj.kdove.commons.domain.KDoveUser;
//import com.kj.kdove.commons.dto.ResponseData;
//import com.kj.kdove.usermatching.retrofit2.DbApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import retrofit2.Call;
//
//import java.io.IOException;
//
//@RestController
//public class Retrofit2test {
//
//
//    @Autowired
//    private DbApi dbApi;
//
//    @GetMapping("/test1/{username}")
//    public KDoveUser test1(@PathVariable String username){
//        Call<ResponseData<KDoveUser>> userinfo = dbApi.getUserinfo(username);
//        //以下是走的异步，可以验证请求成功失败，可以用以完成与主业务逻辑关联不大的其他业务逻辑
////        userinfo.enqueue(new Callback<KDoveUser>() {
////            @Override
////            public void onResponse(Call<KDoveUser> call, Response<KDoveUser> response) {
////                System.out.println(response.body());
////            }
////            @Override
////            public void onFailure(Call<KDoveUser> call, Throwable throwable) {
////                System.out.println("请求失败");
////            }
////        });
//        try {
//            System.out.println(userinfo.execute().body());
//        } catch (IOException e) {
//            System.out.println("失败");
//        }
//        return null;
//    }
//
//}
