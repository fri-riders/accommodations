package com.fri.rso.fririders.accommodations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/environment")
public class EnvTestController {
    @Autowired
    private Environment env;

    @Value("${app.name}")
    String appName;

    @GetMapping
    public String getEnv() {
        return env.toString();
    }

    @RequestMapping(value = "/appName", method = RequestMethod.GET)
    public String getAppname() {
        return appName + " " + env.getProperty("app.name");
    }
}
