package com.hoffmann_g.security_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "customer-service")
public interface CustomerService {
    
    @RequestMapping(method = RequestMethod.GET, value = "api/user")
    public Boolean existsByEmail(@RequestBody String email);

}
