package com.oxygensend.staticdata.infrastructure.fixtures

import com.github.javafaker.Faker
import com.oxygensend.springfixtures.Fixture
import com.oxygensend.springfixtures.FixturesFakerProvider
import com.oxygensend.staticdata.domain.Address
import com.oxygensend.staticdata.domain.AddressRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Stream

@Component
class AddressFixture(
    private val addressRepository: AddressRepository,
    fakerProvider: FixturesFakerProvider
) : Fixture {

    private val random: Random = fakerProvider.random()
    private val faker: Faker = fakerProvider.faker()

    override fun load() {
        val addresses: List<Address> = Stream.generate {
            val address = Address(ObjectId().toHexString())
            address.city = faker.address().cityName()
            address.lat = faker.address().latitude().toDouble()
            address.lon = faker.address().longitude().toDouble()
            address.postalCodes = setOf(faker.address().zipCode())
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