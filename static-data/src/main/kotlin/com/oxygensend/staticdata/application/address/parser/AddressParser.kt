package com.oxygensend.staticdata.application.address.parser

interface AddressParser {
    fun getAddresses(): List<ParsedAddressDto>
}