package com.fri.rso.fririders.accommodations.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix="app")
public class CustomProperties {
    private String name;
    private Boolean healthy;
    private boolean localhost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public boolean isLocalhost() {
        return localhost;
    }

    public void setLocalhost(boolean localhost) {
        this.localhost = localhost;
    }
}
