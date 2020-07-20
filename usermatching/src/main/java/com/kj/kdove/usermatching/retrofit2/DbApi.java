package com.kj.kdove.usermatching.retrofit2;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.usermatching.retrofit2.api.UserInfoProviderApi;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import javax.annotation.Resource;


@Service
public class DbApi {

    @Resource(name = "retrofit1")
    private Retrofit retrofit;

    public Call<KDoveUser> getUserinfo(String username){
        UserInfoProviderApi userInfoProvider = retrofit.create(UserInfoProviderApi.class);
//        Call<ResponseBody> kj = userInfoProvider.getUserInfo(username);
        Call<KDoveUser> userInfo = userInfoProvider.getUserInfo(username);
        return userInfo;
    }


}
