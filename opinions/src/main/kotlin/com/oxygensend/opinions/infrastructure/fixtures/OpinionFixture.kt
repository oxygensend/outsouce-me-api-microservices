package com.oxygensend.opinions.infrastructure.fixtures

import com.github.javafaker.Faker
import com.oxygensend.opinions.context.event.OpinionDetailsRecalculateEvent
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.OpinionRepository
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.springfixtures.Fixture
import org.bson.types.ObjectId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

@Component
class OpinionFixture(
    private val opinionRepository: OpinionRepository,
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : Fixture {
    companion object {
        private val RANDOM: Random = Random(100)
        private val FAKER: Faker = Faker.instance(Locale.UK, RANDOM);
    }


    override fun load() {
        val users = userRepository.findAll();
        val opinions = mutableListOf<Opinion>()
        users.indices.forEach { i ->
            val receiver = users[i];
            (0..RANDOM.nextInt(15)).forEach { _ ->
                val author = getAuthor(receiver, users);
                author?.let {
                    opinions.add(Opinion(ObjectId(), author.id, receiver.id, RANDOM.nextInt(0, 5), FAKER.lorem().characters(0, 100)))
                }
            }

            applicationEventPublisher.publishEvent(OpinionDetailsRecalculateEvent(receiver.id))
        }

        opinionRepository.saveAll(opinions)
    }

    private fun getAuthor(recipient: User, users: List<User>): User? {
        return Stream.generate { users[RANDOM.nextInt(users.size - 1)] }
            .filter { it.id != recipient.id }
            .findFirst()
            .getOrNull()
    }

    override fun isEnabled(): Boolean = opinionRepository.count() == 0.toLong()

    override fun collectionName(): String = "opinions"

}