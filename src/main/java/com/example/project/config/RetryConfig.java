package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
public class RetryConfig {

//    @Bean //빈을 이용한 방법
//    public RetryTemplate retryTemplate() {
//        return new RetryTemplate();
//    }




}
