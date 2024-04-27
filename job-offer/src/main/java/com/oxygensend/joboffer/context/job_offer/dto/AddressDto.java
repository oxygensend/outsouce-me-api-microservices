package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AddressDto(@NotEmpty String postCode,
                         @NotEmpty String city,
                         @NotBlank String lon,
                         @NotBlank String lat) {


    public Address toAddress() {
        return new Address(city, postCode, lon, lat);
    }
}
