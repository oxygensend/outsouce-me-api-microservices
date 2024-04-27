package com.oxygensend.staticdata.infrastructure.openstreetmap

import com.oxygensend.staticdata.config.StaticDataProperties
import com.oxygensend.staticdata.domain.AddressDetailsRepository
import com.oxygensend.staticdata.domain.CoordinatesDto
import com.oxygensend.staticdata.domain.Address
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
internal final class AddressDetailsOSMRepository(staticDataProperties: StaticDataProperties) : AddressDetailsRepository {
    private val osmUri: String;

    init {
        this.osmUri = staticDataProperties.openStreetMapUrl!!
    }
    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    override fun getCoordinates(address: Address): CoordinatesDto? {
        val response = RestClient.create()
            .get()
            .uri("$osmUri&postalcode={postalCode}", address.postalCodes.first())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(typeReference<List<CoordinatesDto>>())

        return response?.firstOrNull()
    }
}