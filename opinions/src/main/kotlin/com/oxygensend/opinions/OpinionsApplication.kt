package com.oxygensend.opinions

import com.oxygensend.commonspring.exception.ExceptionConfiguration
import com.oxygensend.commonspring.request_context.RequestContextConfiguration
import io.swagger.v3.oas.models.media.StringSchema
import org.bson.types.ObjectId
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(ExceptionConfiguration::class, RequestContextConfiguration::class)
@SpringBootApplication
class OpinionsApplication

fun main(args: Array<String>) {
    SpringDocUtils.getConfig().replaceWithSchema(ObjectId::class.java, StringSchema())
    runApplication<OpinionsApplication>(*args)
}
