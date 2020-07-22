package com.kj.kdove.usermatching.retrofit2.api;

import com.kj.kdove.commons.domain.KDoveUser;

import com.kj.kdove.commons.dto.ResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;

public interface UserInfoProviderApi {

    @GET("us/data/getuser/{username}")
    Call<ResponseData<KDoveUser>> getUserInfo(@Path("username") String username);

    @POST("us/data/adduser/reg")
    Call<Map<String,Integer>>  addUser(@Body KDoveUser kDoveUser);
}
