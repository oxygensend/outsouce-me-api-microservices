package com.oxygensend.joboffer.application.job_offer.dto;

import com.oxygensend.joboffer.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AddressDto(@NotEmpty String postCode,
                         @NotEmpty String city,
                         @NotBlank Double lon,
                         @NotBlank Double lat) {


    public Address toAddress() {
        return new Address(city, postCode, lon, lat);
    }
}
