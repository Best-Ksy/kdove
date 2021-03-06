package com.kj.kdove.usermatching.controller;
import com.kj.kdove.commons.dto.ResponseData;
import com.kj.kdove.commons.enums.MatchingEnum;
import com.kj.kdove.usermatching.service.api.MatchingService;
import com.kj.kdove.usermatching.service.api.UserRDBService;
import com.kj.kdove.usermatching.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/matching")
public class UserMatchingController {

    @Autowired
    private SimpleDateFormat simpleDateFormat;
    @Autowired
    private UserRDBService userRDBService;
    @Autowired
    private MatchingService matchingService;

    @CrossOrigin(value = "*")
    @GetMapping(value = "/getmatching/{userid}")
    public ResponseData<Map<String,String>> MatchingFunction(@PathVariable String userid, HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);

        /*
        * 这里还需要判断是不是匹配频率太快，
        * 判断缓存中是否已经存在，缓存中存在相同的key不许匹配
        * 缓存中的key过期时间是20s，也就是说必须至少间隔20s才能下一次匹配
        * */

        //存入reids
        boolean flag = userRDBService.addUserIdtoRDB(userid,ipAddr);
        try {
            //避免redis处于无数据状态，增加匹配基数
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (flag){
            Map<String, String> matchmap = matchingService.MatchingUser(userid);
            if (matchmap.size() == 0){
                userRDBService.removeUserIdFromRDB(userid);
                return new ResponseData<>(
                        MatchingEnum.MATCHING_FALSE.getCode(),
                        MatchingEnum.MATCHING_FALSE.getMessage(),
                        null
                );
            }else {
                //匹配成功，在内存中删除自己的匹配信息
//                if (userRDBService.getValueByUserIdFromRDB(userid) != null){
//
//                    userRDBService.removeUserIdFromRDB(userid);
//                }
                String date = simpleDateFormat.format(new Date());
                matchmap.put("date",date);
                return new ResponseData<>(
                        MatchingEnum.MATCHING_SUCCESS.getCode(),
                        MatchingEnum.MATCHING_SUCCESS.getMessage(),
                        matchmap
                );
            }
        }else {
            return new ResponseData<>(
                    MatchingEnum.MATCHING_ERRO.getCode(),
                    MatchingEnum.MATCHING_ERRO.getMessage(),
                    null
            );
        }
    }

}
