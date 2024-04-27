package com.oxygensend.staticdata.context.address

import com.oxygensend.staticdata.domain.AddressDetailsRepository
import com.oxygensend.staticdata.context.address.parser.AddressParser
import com.oxygensend.staticdata.context.address.parser.ParsedAddressDto
import com.oxygensend.staticdata.domain.Address
import com.oxygensend.staticdata.domain.AddressRepository
import com.oxygensend.staticdata.domain.exception.AlreadyLoadingException
import kotlinx.coroutines.*
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AddressService(
    private val addressRepository: AddressRepository,
    private val addressParser: AddressParser,
    private val addressDetailsRepository: AddressDetailsRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(AddressService::class.java)
    private var job: Job? = null

    fun findAllAddresses(search: String?): List<AddressView> = addressRepository.findAll(search)
        .map { address -> AddressView.from(address) }

    fun forceStop() {
        job?.cancel("DD");
    }

    suspend fun loadAddresses() {
        if (job?.isActive == true) {
            throw AlreadyLoadingException("Loading is already in progress")
        }

        job = CoroutineScope(Dispatchers.IO).launch {
            val startTimestamp = Instant.now().toEpochMilli();
            logger.info("Started loading addresses to db")

            val addresses = addressParser.getAddresses()
            val batch = mutableListOf<Address>()
            val addressesCount = addresses.size
            var batchCount = 0

            run batchProcessing@{
                addresses.forEach { dto ->
                    if (job?.isCancelled == true) {
                        logger.info("Loading stopped")
                        return@batchProcessing
                    }

                    val address = updateAddress(dto)
                    batch.add(address)
                    batchCount++
                    if (batchCount % 10 == 0) {
                        addressRepository.saveBatch(batch)
                        logger.info("$batchCount/$addressesCount elements saved into database")
                        batch.clear()
                    }
                }

            }

            addressRepository.saveBatch(batch)
            val endTimestamp = Instant.now().toEpochMilli()
            logger.info("Finished loading addresses in time {} ms", endTimestamp - startTimestamp)
        }
    }

    private fun updateAddress(dto: ParsedAddressDto): Address {
        val address = addressRepository.findByCity(dto.city) ?: Address(id = ObjectId().toHexString())
        address.city = dto.city
        address.postalCodes = dto.postalCodes
        val cords = addressDetailsRepository.getCoordinates(address)
        address.lat = cords?.lat
        address.lon = cords?.lon
        return address
    }
}