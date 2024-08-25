package com.oxygensend.joboffer.application.job_offer.dto;

import com.oxygensend.joboffer.domain.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record AddressDto(@NotEmpty String postCode,
                         @NotEmpty String city,
                         @Positive Double lon,
                         @Positive Double lat) {


    public Address toAddress() {
        return new Address(city, postCode, lon, lat);
    }
}
