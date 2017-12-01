package com.fri.rso.fririders.accommodations.controller;

import com.fri.rso.fririders.accommodations.config.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final CustomProperties properties;

    private final RestTemplate restTemplate;
    private final ConfigurableEnvironment env;

    @Value("${app.name}")
    String appName;

    @Autowired
    public TestController(RestTemplate restTemplate, ConfigurableEnvironment env, CustomProperties properties) {
        this.restTemplate = restTemplate;
        this.env = env;
        this.properties = properties;
    }

    @GetMapping
    public String getEnv() {
        return env.toString();
    }

    @RequestMapping(value = "/appName", method = RequestMethod.GET)
    public String getAppname() {
        return appName + " " + env.getProperty("app.name");
    }

    @RequestMapping(value = "/restart", method = RequestMethod.POST)
    public ResponseEntity restartApp() {
        properties.setHealthy(!properties.getHealthy());
        return ResponseEntity.ok().body("Make healthy=" + properties.getHealthy());
    }
}
