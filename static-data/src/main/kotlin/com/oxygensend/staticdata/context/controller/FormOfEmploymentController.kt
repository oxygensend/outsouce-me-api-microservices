package com.oxygensend.staticdata.context.controller

import com.oxygensend.staticdata.context.dto.FormOfEmploymentView
import com.oxygensend.staticdata.domain.FormOfEmployment
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Form of Employment")
@RestController
@RequestMapping("/api/v1/static-data")
class FormOfEmploymentController {


    @GetMapping("/form-of-employments")
    fun getFormOfEmployments(): List<FormOfEmploymentView> = FormOfEmployment.entries
        .map { formOfEmployment -> FormOfEmploymentView.from(formOfEmployment) }
        .toList();

}