package com.oxygensend.staticdata.context.technology.loader

import com.oxygensend.staticdata.config.StaticDataProperties
import com.oxygensend.staticdata.domain.Technology
import com.oxygensend.staticdata.domain.TechnologyRepository
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.File

@Component
internal class TechnologiesLoader(
    private val technologyRepository: TechnologyRepository,
    staticDataProperties: StaticDataProperties,
) {

    private final val csvFile: File
    private final val logger: Logger = LoggerFactory.getLogger(TechnologiesLoader::class.java)

    init {
        this.csvFile = staticDataProperties.technologyCsvFile ?: throw IllegalArgumentException("Csv file cannot be null")
    }

    @EventListener(value = [ApplicationReadyEvent::class])
    fun load() {
        logger.info("Started loading technologies to the database.")

        val newTechnologies = csvFile.bufferedReader()
            .lineSequence()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { mapToTechnologyCsvDto(it) }
            .filter { !technologyRepository.existsByName(it.name) }
            .map { mapToTechnology(it) }
            .toList()

        technologyRepository.saveAll(newTechnologies)
        logger.info("{} new technologies inserted into database", newTechnologies.size)
    }

    private fun mapToTechnologyCsvDto(text: String): TechnologyCsvDto {
        val (name, featured) = text.split(',', ignoreCase = false, limit = 2)
        return TechnologyCsvDto(name.trim(), featured.trim().toBoolean())
    }

    private fun mapToTechnology(technologyCsvDto: TechnologyCsvDto) =
        Technology(id = ObjectId().toHexString(), name = technologyCsvDto.name, featured = technologyCsvDto.featured)

}
