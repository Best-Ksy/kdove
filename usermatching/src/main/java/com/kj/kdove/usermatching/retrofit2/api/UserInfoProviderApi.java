package com.kj.kdove.usermatching.retrofit2.api;

import com.kj.kdove.commons.domain.KDoveUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInfoProviderApi {

    @GET("us/data/getuser/{username}")
    Call<KDoveUser> getUserInfo(@Path("username") String username);
}
