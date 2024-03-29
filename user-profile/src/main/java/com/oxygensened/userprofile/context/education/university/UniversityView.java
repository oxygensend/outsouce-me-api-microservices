package com.oxygensened.userprofile.context.education.university;

import com.oxygensened.userprofile.domain.University;

public record UniversityView(Long id, String name) {

    public static UniversityView from(University university) {
        return new UniversityView(university.id(), university.name());
    }
}
