package com.hoffmann_g.order_service.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record OrderRequest(

    @Valid
    @NotBlank(message = "Payment type cannot be blank")
    String paymentType) {
}
