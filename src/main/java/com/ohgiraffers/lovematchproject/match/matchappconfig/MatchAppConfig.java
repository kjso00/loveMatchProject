package com.ohgiraffers.lovematchproject.match.matchappconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MatchAppConfig {
    @Bean(name = "matchRestTemplate")
    public RestTemplate matchRestTemplate() {
        return new RestTemplate();
    }
}
