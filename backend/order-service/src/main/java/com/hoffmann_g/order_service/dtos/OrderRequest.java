package com.hoffmann_g.order_service.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
    @Valid
    @NotNull(message = "Customer ID cannot be null")
    Long customerId,

    @Valid
    @NotBlank(message = "Payment type cannot be blank")
    String paymentType) {
}
