package com.oxygensend.joboffer.context.job_offer.dto.view;


import com.oxygensend.joboffer.domain.entity.Address;

public record AddressView(String postCode, String city) {

    public static AddressView from(Address address) {
        return new AddressView(address.postCode(), address.city());
    }
}
