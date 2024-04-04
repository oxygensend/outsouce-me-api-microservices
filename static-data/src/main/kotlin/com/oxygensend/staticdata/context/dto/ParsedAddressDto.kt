package com.oxygensend.staticdata.context.dto

data class ParsedAddressDto(
    val city: String,
    val postalCodes: Set<String>
) {
}

