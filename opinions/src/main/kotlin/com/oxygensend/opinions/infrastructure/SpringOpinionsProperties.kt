package com.oxygensend.opinions.infrastructure

import com.oxygensend.opinions.application.OpinionsProperties
import com.oxygensend.opinions.domain.event.Topics
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "opinions")
data class SpringOpinionsProperties(@field:NotNull override val topics: Map<Topics, String>) : OpinionsProperties {

    companion object {
        private const val TOPICS_SIZE = 1;
    }

    init {
        if (topics.size != TOPICS_SIZE) {
            throw IllegalArgumentException("Invalid topics configuration")
        }
    }
}