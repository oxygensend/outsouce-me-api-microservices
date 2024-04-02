package com.oxygensend.staticdata.context.dto

import com.oxygensend.staticdata.domain.FormOfEmployment

data class FormOfEmploymentView(
    val name: String,
    val displayName: String
) {

    companion object {
        fun from(formOfEmployment: FormOfEmployment): FormOfEmploymentView = FormOfEmploymentView(formOfEmployment.name, formOfEmployment.displayName)
    }
}
