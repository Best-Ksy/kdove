package com.kj.kdove.usermatching.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Configuration
public class RetrofitConfig {

    @Value("${spring.reactorurl.dbserviceurl}")
    private String dbServiceUrl;

    @Bean(name = "retrofit1")
    public Retrofit getRetrofit(){
        Retrofit build = new Retrofit.Builder()
                .baseUrl(dbServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return build;
    }
}
