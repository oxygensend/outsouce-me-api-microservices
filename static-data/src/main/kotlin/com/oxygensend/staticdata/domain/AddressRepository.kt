package com.oxygensend.staticdata.domain

interface AddressRepository {

    fun findAll(search: String?): List<Address>
    fun findAll(): List<Address>
    fun save(address: Address): Address
    fun findByCity(city: String): Address?
    fun saveBatch(addresses: List<Address>)
    fun count(): Long
    fun saveAll(addresses: List<Address>)
}