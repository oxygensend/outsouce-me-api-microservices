package com.oxygensend.staticdata.infrastructure.fixtures

import com.github.javafaker.Faker
import com.oxygensend.springfixtures.Fixture
import com.oxygensend.staticdata.domain.Address
import com.oxygensend.staticdata.domain.AddressRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Stream

@Component
class AddressFixture(private val addressRepository: AddressRepository) : Fixture {

    companion object {
        private val RANDOM: Random = Random(100)
        private val FAKER: Faker = Faker.instance(Locale.UK, RANDOM)
    }

    override fun load() {
        val addresses: List<Address> = Stream.generate {
            val address = Address(ObjectId().toHexString())
            address.city = FAKER.address().cityName()
            address.lat = FAKER.address().latitude().toDouble()
            address.lon = FAKER.address().longitude().toDouble()
            address.postalCodes = setOf(FAKER.address().zipCode())
            return@generate address
        }
            .distinct()
            .limit(500)
            .toList()

        addressRepository.saveAll(addresses);
    }

    override fun isEnabled(): Boolean = addressRepository.count() == 0.toLong()

    override fun collectionName(): String {
        return "addresses"
    }
}