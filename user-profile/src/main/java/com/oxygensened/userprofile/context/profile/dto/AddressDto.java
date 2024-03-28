package com.oxygensened.userprofile.context.profile.dto;

import com.oxygensened.userprofile.domain.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressDto(@NotBlank String postCode,
                         @NotBlank String city,
                         @NotBlank String lon,
                         @NotBlank String lat) {


    public Address toAddress() {
        return new Address(city, postCode, lon, lat);
    }
}