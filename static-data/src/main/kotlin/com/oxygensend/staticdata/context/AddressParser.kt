package com.oxygensend.staticdata.context

import com.oxygensend.staticdata.context.dto.ParsedAddressDto

interface AddressParser {
    fun getAddresses(): List<ParsedAddressDto>
}