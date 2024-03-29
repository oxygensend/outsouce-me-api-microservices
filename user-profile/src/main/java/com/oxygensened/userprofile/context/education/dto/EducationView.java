package com.oxygensened.userprofile.context.education.dto;

import com.oxygensened.userprofile.domain.Education;
import com.oxygensened.userprofile.domain.University;
import java.time.LocalDate;

public record EducationView(Long id,
                            UniversityView university,
                            LocalDate startDate,
                            LocalDate endDate,
                            String fieldOfStudy,
                            String title,
                            Double grade,
                            String description) {

    public static EducationView from(Education education) {
        var university = education.university() != null ? UniversityView.from(education.university()) : null;
        return new EducationView(education.id(), university, education.startDate(), education.endDate(), education.fieldOfStudy(),
                                 education.title(), education.grade(), education.description());
    }

    public record UniversityView(String name) {
        public static UniversityView from(University university) {
            return new UniversityView(university.name());
        }

    }
}
