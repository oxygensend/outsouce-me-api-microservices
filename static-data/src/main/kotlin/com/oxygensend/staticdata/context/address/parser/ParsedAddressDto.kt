package com.oxygensend.staticdata.context.address.parser

data class ParsedAddressDto(
    val city: String,
    val postalCodes: Set<String>
) {
}

