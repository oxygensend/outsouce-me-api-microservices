package com.oxygensened.userprofile.application.profile.dto;

import com.oxygensened.userprofile.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressDto(@NotBlank String postCode,
                         @NotBlank String city,
                         @NotBlank String lon,
                         @NotBlank String lat) {


    public Address toAddress() {
        return new Address(city, postCode, lon, lat);
    }
}
