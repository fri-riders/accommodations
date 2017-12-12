package com.fri.rso.fririders.accommodations.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogContextInterceptor(applicationContext));
    }
}
