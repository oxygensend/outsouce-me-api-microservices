package com.oxygensend.staticdata.context.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "About Us")
@RestController
@RequestMapping("/api/v1/static-data")
class AboutUsController {
}