package com.oxygensend.opinions.infrastructure.fixtures

import com.github.javafaker.Faker
import com.oxygensend.opinions.application.event.OpinionDetailsRecalculateEvent
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.OpinionRepository
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.springfixtures.Fixture
import com.oxygensend.springfixtures.FixturesFakerProvider
import org.bson.types.ObjectId
import org.springframework.context.ApplicationEventPublisher
import java.util.*
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

class OpinionFixture(
    private val opinionRepository: OpinionRepository,
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    fakerProvider: FixturesFakerProvider
) : Fixture {
    private val random: Random = fakerProvider.random()
    private val faker: Faker = fakerProvider.faker()


    override fun load() {
        val users = userRepository.findAll();
        val opinions = mutableListOf<Opinion>()
        users.indices.forEach { i ->
            val receiver = users[i];
            (0..random.nextInt(15)).forEach { _ ->
                val author = getAuthor(receiver, users);
                author?.let {
                    opinions.add(Opinion(ObjectId(), author.id, receiver.id, random.nextInt(0, 5), faker.lorem().paragraph(random.nextInt(0,3))))
                }
            }

            applicationEventPublisher.publishEvent(OpinionDetailsRecalculateEvent(receiver.id))
        }

        opinionRepository.saveAll(opinions)
    }

    private fun getAuthor(recipient: User, users: List<User>): User? {
        return Stream.generate { users[random.nextInt(users.size - 1)] }
            .filter { it.id != recipient.id }
            .findFirst()
            .getOrNull()
    }

    override fun isEnabled(): Boolean = opinionRepository.count() == 0.toLong()

    override fun collectionName(): String = "opinions"

}