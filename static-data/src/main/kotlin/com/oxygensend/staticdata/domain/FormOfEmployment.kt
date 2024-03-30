package com.oxygensend.staticdata.domain

enum class FormOfEmployment(public val displayName: String) {
    FULL_TIME("Pełny etat"),
    PART_TIME("Niepełny etat"),
    CONTRACT("Umowa zlecenie"),
    FREELANCE("Freelance"),
    INTERNSHIP("Staż"),
    APPRENTICESHIP("Praktyka"),
    TEMPORARY("Praca tymczasowa"),
    OTHER("Inne");
}
