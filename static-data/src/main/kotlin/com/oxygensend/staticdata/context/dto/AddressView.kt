package com.oxygensend.staticdata.context.dto

import com.oxygensend.staticdata.domain.Address

data class AddressView(val city: String) {
    companion object {
        fun from(address: Address) = AddressView(address.city)
    }
}