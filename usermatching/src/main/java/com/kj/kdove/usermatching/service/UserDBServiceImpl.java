package com.kj.kdove.usermatching.service;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.matching.UserEnum;
import com.kj.kdove.usermatching.retrofit2.DbApi;
import com.kj.kdove.usermatching.service.api.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;

@Service
public class UserDBServiceImpl implements UserDBService {

    @Autowired
    private DbApi dbApi;

    @Override
    public ResponseData<KDoveUser> getUserByUserName(String username) {
        if (username == null || username.equals("")){
            return new ResponseData<>(
                    UserEnum.SELECT_USER_BY_USERNAME_PARAM_ERRO.getCode(),
                    UserEnum.SELECT_USER_BY_USERNAME_PARAM_ERRO.getMessage(),
                    null
            );
        }
        Call<ResponseData<KDoveUser>> userinfo = dbApi.getUserinfo(username);
        try {
            //不管有没有查询到用户，都直接原生返回
            return userinfo.execute().body();
        } catch (IOException e) {
            //如果出现网络错误等情况，返回网络错误
            return new ResponseData<>(
                    UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getCode(),
                    UserEnum.SELECT_USER_BY_USERNAME_IOEXCEPTION.getMessage(),
                    null
            );
        }
    }

    @Override
    public Boolean addUser(KDoveUser kDoveUser) {

        Call<Map<String, Integer>> mapCall = dbApi.addUser(kDoveUser);
        try {
            Map<String, Integer> body = mapCall.execute().body();
            Integer state = body.get("state");
            return state == 1;
        } catch (IOException e) {
            return false;
        }
    }
}
