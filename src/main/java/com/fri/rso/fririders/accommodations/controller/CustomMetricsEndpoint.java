package com.fri.rso.fririders.accommodations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomMetricsEndpoint extends AbstractEndpoint<Map<String, Object>> {

    private MetricsEndpoint metricsEndpoint;

    @Autowired
    public CustomMetricsEndpoint(MetricsEndpoint metricsEndpoint) {
        super("custommetrics");
        this.metricsEndpoint = metricsEndpoint;
    }

    public Map<String, Object> invoke() {
        HashMap<String, Object> map =  new HashMap<>(this.metricsEndpoint.invoke());
        return map.entrySet().stream()
                .filter(e -> e.getKey()
                .contains("servo.services"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}