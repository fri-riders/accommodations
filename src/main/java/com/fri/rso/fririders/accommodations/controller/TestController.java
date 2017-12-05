package com.fri.rso.fririders.accommodations.controller;

import com.fri.rso.fririders.accommodations.config.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final CustomProperties properties;

    private final ConfigurableEnvironment env;

    @Value("${app.name}")
    String appName;

    @Autowired
    public TestController(ConfigurableEnvironment env, CustomProperties properties) {
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

    @RequestMapping(value = "/fibonnacci/{n}", method = RequestMethod.GET)
    public ResponseEntity calculateFibonnacci(@PathVariable int n) {
      return ResponseEntity.ok(fibonacci(n));
    }

    private int fibonacci(int n)  {
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
