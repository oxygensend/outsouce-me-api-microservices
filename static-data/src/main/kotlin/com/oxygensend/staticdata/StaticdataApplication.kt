package com.oxygensend.staticdata

import com.oxygensend.commonspring.exception.ExceptionConfiguration
import com.oxygensend.commonspring.request_context.RequestContextConfiguration
import com.oxygensend.commonspring.storage.StorageConfiguration
import com.oxygensend.staticdata.infrastructure.StaticDataProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import

@EnableCaching
@EnableConfigurationProperties(StaticDataProperties::class)
@SpringBootApplication
@Import(ExceptionConfiguration::class, RequestContextConfiguration::class, StorageConfiguration::class)
class StaticdataApplication

fun main(args: Array<String>) {
    runApplication<StaticdataApplication>(*args)
}
