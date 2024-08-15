package com.hoffmann_g.api_gateway.dtos;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserResponse {
    
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
}
