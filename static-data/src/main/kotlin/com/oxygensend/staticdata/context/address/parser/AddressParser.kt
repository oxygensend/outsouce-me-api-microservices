package com.oxygensend.staticdata.context.address.parser

interface AddressParser {
    fun getAddresses(): List<ParsedAddressDto>
}