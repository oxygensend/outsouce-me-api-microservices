package com.oxygensend.staticdata.domain

interface AddressRepository {

    fun findAll(search: String?): List<Address>
    fun save(address: Address): Address
}