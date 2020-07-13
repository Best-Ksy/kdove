package com.kj.kdove.usermatching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Random;

@Configuration
public class CommonConfig {

    @Bean
    public SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat;
    }
    @Bean
    public Random getRandom(){
        return new Random();
    }

}
