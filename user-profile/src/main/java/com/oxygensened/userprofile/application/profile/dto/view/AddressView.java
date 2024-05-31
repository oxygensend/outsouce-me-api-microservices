package com.oxygensened.userprofile.application.profile.dto.view;

import com.oxygensened.userprofile.domain.entity.Address;

public record AddressView(String postCode, String city) {

    public static AddressView from(Address address) {
        return new AddressView(address.postCode(), address.city());
    }
}
