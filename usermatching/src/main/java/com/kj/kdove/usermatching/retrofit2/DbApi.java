package com.kj.kdove.usermatching.retrofit2;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.usermatching.retrofit2.api.UserInfoProviderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import javax.annotation.Resource;
import java.util.Map;


@Service
public class DbApi {

    @Resource(name = "retrofit1")
    private Retrofit retrofit;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Call<ResponseData<KDoveUser>> getUserinfo(String username){
        UserInfoProviderApi userInfoProvider = retrofit.create(UserInfoProviderApi.class);
        return userInfoProvider.getUserInfo(username);
    }

    public Call<Map<String,Integer>> addUser(KDoveUser kDoveUser){
        UserInfoProviderApi userInfoProvider = retrofit.create(UserInfoProviderApi.class);
        kDoveUser.setPassword(passwordEncoder.encode(kDoveUser.getPassword()));
        return userInfoProvider.addUser(kDoveUser);
    }


}
