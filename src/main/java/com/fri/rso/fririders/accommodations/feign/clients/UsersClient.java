package com.fri.rso.fririders.accommodations.feign.clients;

import com.fri.rso.fririders.accommodations.data.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@FeignClient("dev/rsousers")
public interface UsersClient {
    @RequestMapping(method = RequestMethod.GET, value = "/v1/users/{userId}")
    List<User> getUsers(@PathVariable("userId") String userId);
}