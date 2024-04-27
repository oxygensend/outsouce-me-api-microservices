package com.oxygensend.staticdata.domain

interface AddressDetailsRepository {
    fun getCoordinates(address: Address): CoordinatesDto?
}