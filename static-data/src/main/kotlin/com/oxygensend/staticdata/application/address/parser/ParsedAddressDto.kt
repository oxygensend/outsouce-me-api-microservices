package com.oxygensend.staticdata.application.address.parser

data class ParsedAddressDto(
    val city: String,
    val postalCodes: Set<String>
) {
}

