package com.oxygensend.joboffer.context.job_offer.dto.view;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.oxygensend.joboffer.domain.entity.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressView(String postCode, String city, String lon, String lat) {

    public static AddressView from(Address address) {
        return new AddressView(address.postCode(), address.city(), null, null);
    }

    public static AddressView withCoords(Address address) {
        return new AddressView(address.postCode(), address.city(), address.lon(), address.lat());
    }
}
