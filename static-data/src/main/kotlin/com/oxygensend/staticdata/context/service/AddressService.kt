package com.oxygensend.staticdata.context.service

import com.oxygensend.staticdata.context.dto.AddressView
import com.oxygensend.staticdata.domain.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: AddressRepository) {

    fun findAllAddresses(search: String?): List<AddressView> = addressRepository.findAll(search)
        .map { address -> AddressView.from(address) }

    fun loadAddresses() {
        TODO("Not yet implemented")
    }
}