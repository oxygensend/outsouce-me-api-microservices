package com.oxygensend.staticdata

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ExceptionConfiguration::class)
class StaticdataApplication

fun main(args: Array<String>) {
    runApplication<StaticdataApplication>(*args)
}
