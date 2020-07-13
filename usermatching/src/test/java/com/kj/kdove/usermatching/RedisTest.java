package com.kj.kdove.usermatching;

import com.kj.kdove.usermatching.redis.api.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test1(){
        redisService.set(String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()));
    }

    @Test
    public void test2(){
        String test = redisService.get("159461991719");
        System.out.println(test);
    }

    @Test
    public void test3(){
        boolean expire = redisService.expire("test", 10);
        System.out.println(expire);
    }

    @Test
    public void test4(){
        Set<String> redisKeys = redisService.getRedisKeys();
        for (String key:redisKeys){
            System.out.println(key);
        }
    }

    @Test
    public void test5(){
        Random random = new Random();

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = random.nextInt(5);
            System.out.println(i);
        }


    }

}
