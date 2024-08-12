package com.hoffmann_g.product_service.dtos;

import lombok.Builder;

@Builder
public record ProductResponse(
    Long id,
    String name,
    String description,
    Long price) {

}
