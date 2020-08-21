package com.kj.kdove.dbservice.service;

import com.kj.kdove.commons.domain.KDoveUser;
import com.kj.kdove.dbservice.mapper.KDoveUserMapper;
import com.kj.kdove.dbservice.service.api.UserInfoService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private KDoveUserMapper kDoveUserMapper;

    @Override
    public Integer addUser(KDoveUser kDoveUser) {
        initKDoveUser(kDoveUser);
        int insert = kDoveUserMapper.insert(kDoveUser);
        return insert;
    }

    @Override
    public KDoveUser getUserByUserName(String userName) {
        Example example = new Example(KDoveUser.class);
        example.createCriteria().andEqualTo("username",userName);
        return kDoveUserMapper.selectOneByExample(example);
    }

    private void initKDoveUser(KDoveUser kDoveUser){
        if (kDoveUser.getRegdate() == null){
            kDoveUser.setRegdate(new Date());
        }
        if (kDoveUser.getUcode() == null){
            String current = String.valueOf(System.currentTimeMillis());
            kDoveUser.setUcode(current.substring(current.length()/2));
        }
    }
}
