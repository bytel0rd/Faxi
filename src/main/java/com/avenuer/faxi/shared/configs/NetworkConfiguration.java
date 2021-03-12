package com.avenuer.faxi.shared.configs;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetworkConfiguration {

    @Bean
    public OkHttpClient createHttpClient() {
        return new OkHttpClient();
    }

}
