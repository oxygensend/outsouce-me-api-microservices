package com.oxygensend.joboffer.infrastructure.services.staticdata.dto;

import java.util.List;

public record AddressDto(String city, List<String> postalCodes, Double lat, Double lon) {
}
