package com.fri.rso.fririders.accommodations.feign.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notifications")
public interface NotificationsClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/notifications/mail")
    String sendNotification(
            @RequestParam("recipient") final String recipient,
            @RequestParam("subject") final String subject,
            @RequestParam("message") final String message);
}