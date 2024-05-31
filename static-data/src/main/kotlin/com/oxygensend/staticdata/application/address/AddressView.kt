package com.oxygensend.staticdata.application.address

import com.oxygensend.staticdata.domain.Address

data class AddressView(val city: String, val lon: Double?, val lat: Double?) {
    companion object {
        fun from(address: Address) = AddressView(address.city, address.lon, address.lat)
    }
}