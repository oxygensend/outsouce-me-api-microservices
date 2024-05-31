package com.oxygensend.staticdata.application.stat

import com.oxygensend.staticdata.domain.FormOfEmployment
import com.oxygensend.staticdata.domain.WorkType

data class EnumView(
    val name: String,
    val displayName: String
) {

    companion object {
        fun from(formOfEmployment: FormOfEmployment): EnumView = EnumView(formOfEmployment.name, formOfEmployment.displayName)
        fun from(workType: WorkType): EnumView = EnumView(workType.name, workType.displayName)
    }
}
