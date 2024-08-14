package com.hoffmann_g.customer_service.dtos;

public record AddressResponse(

    Long id,
    String country,
    String city,
    String postalCode,
    String street,
    String addressNumber,
    String addressReference
) {

}
