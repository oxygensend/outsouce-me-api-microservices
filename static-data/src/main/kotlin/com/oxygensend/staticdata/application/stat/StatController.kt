package com.oxygensend.staticdata.application.stat

import com.oxygensend.staticdata.domain.FormOfEmployment
import com.oxygensend.staticdata.domain.WorkType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Form of Employment")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/static-data")
internal class StatController {

    @GetMapping("/form-of-employments")
    fun getFormOfEmployments(): List<EnumView> = FormOfEmployment.entries
        .map { formOfEmployment -> EnumView.from(formOfEmployment) }
        .toList();


    @GetMapping("/work-types")
    fun getWorkTypes(): List<EnumView> = WorkType.entries
        .map { workType -> EnumView.from(workType) }
        .toList();

}