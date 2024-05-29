package com.oxygensend.staticdata.context.address

import com.oxygensend.staticdata.domain.Address

data class AddressWithPostalCodesView(
    val city: String,
    var postalCodes: List<String>,
    var lon: Double?,
    var lat: Double?
) {
    companion object {
        fun from(address: Address) = AddressWithPostalCodesView(address.city, address.postalCodes.toList(), address.lon, address.lat)
    }
}
