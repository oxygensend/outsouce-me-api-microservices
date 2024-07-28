package com.oxygensend.staticdata.application.technology.loader

import com.oxygensend.staticdata.domain.Technology
import com.oxygensend.staticdata.domain.TechnologyRepository
import com.oxygensend.staticdata.infrastructure.StaticDataProperties
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.cache.CacheManager
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import java.nio.charset.Charset

@Component
internal class TechnologiesLoader(
    private val technologyRepository: TechnologyRepository,
    private val cacheManager: CacheManager,
    staticDataProperties: StaticDataProperties,
) {

    private final val csvFile: Resource
    private final val logger: Logger = LoggerFactory.getLogger(TechnologiesLoader::class.java)

    init {
        this.csvFile =
            staticDataProperties.technologyCsvFile ?: throw IllegalArgumentException("Csv file cannot be null")
    }

    @EventListener(value = [ApplicationReadyEvent::class])
    fun load() {
        logger.info("Started loading technologies to the database.")

        val newTechnologies = StreamUtils.copyToString(csvFile.inputStream, Charset.defaultCharset())
            .lineSequence()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { mapToTechnologyCsvDto(it) }
            .filter { !technologyRepository.existsByName(it.name) }
            .map { mapToTechnology(it) }
            .toList()

        if (newTechnologies.isEmpty()) {
            logger.info("No new technologies to insert into database")
            return
        }

        technologyRepository.saveAll(newTechnologies)
        cacheManager.getCache("technologies")?.clear()
        logger.info("{} new technologies inserted into database", newTechnologies.size)
    }

    private fun mapToTechnologyCsvDto(text: String): TechnologyCsvDto {
        val (name, featured) = text.split(',', ignoreCase = false, limit = 2)
        return TechnologyCsvDto(name.trim(), featured.trim().toBoolean())
    }

    private fun mapToTechnology(technologyCsvDto: TechnologyCsvDto) =
        Technology(id = ObjectId().toHexString(), name = technologyCsvDto.name, featured = technologyCsvDto.featured)

}
