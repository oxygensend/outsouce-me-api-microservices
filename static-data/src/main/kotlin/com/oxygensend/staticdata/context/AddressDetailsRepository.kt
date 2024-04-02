package com.oxygensend.staticdata.context

import com.oxygensend.staticdata.context.dto.CoordinatesDto
import com.oxygensend.staticdata.domain.Address

interface AddressDetailsRepository {
    fun getCoordinates(address: Address): CoordinatesDto?
}