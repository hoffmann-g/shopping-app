package com.hoffmann_g.coupon_service.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "coupon")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Coupon {
    
    @Id
    private String code;
    private Integer discountPercentage;
    private LocalDateTime expiresAt;
    private Integer uses;
}
