package com.hoffmann_g.security_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hoffmann_g.security_service.dtos.UserRequest;

@FeignClient(name = "customer-service")
public interface CustomerService {

    @RequestMapping(method = RequestMethod.POST, value = "api/user")
    public void saveUser(@RequestBody UserRequest request);

}
