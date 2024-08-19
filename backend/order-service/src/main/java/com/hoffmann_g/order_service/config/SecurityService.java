package com.hoffmann_g.order_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("security-service")
public interface SecurityService {

    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/security/validate-token/id")
    public Long getUserId(@RequestHeader("Authorization") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/security/validate-token/email")
    public String getUserEmail(@RequestHeader("Authorization") String token);
}
