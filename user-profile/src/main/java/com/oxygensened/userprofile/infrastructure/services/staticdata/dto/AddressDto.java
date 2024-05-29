package com.oxygensened.userprofile.infrastructure.services.staticdata.dto;

import java.util.List;

public record AddressDto(String city, List<String> postalCodes, String lat, String lon) {
}
