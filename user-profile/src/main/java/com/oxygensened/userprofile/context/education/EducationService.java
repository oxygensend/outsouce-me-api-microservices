package com.oxygensened.userprofile.context.education;

import com.oxygensened.userprofile.context.education.dto.CreateEducationRequest;
import com.oxygensened.userprofile.context.education.dto.EducationView;
import com.oxygensened.userprofile.context.education.dto.UpdateEducationRequest;
import com.oxygensened.userprofile.domain.Education;
import com.oxygensened.userprofile.domain.EducationRepository;
import com.oxygensened.userprofile.domain.University;
import com.oxygensened.userprofile.domain.UniversityRepository;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.EducationNotFoundException;
import com.oxygensened.userprofile.domain.exception.NoSuchUniversityException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.infrastructure.jackson.JsonNullableWrapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class EducationService {
    private final EducationRepository educationRepository;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    public EducationService(EducationRepository educationRepository, UniversityRepository universityRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
    }

    public EducationView createEducation(Long userId, CreateEducationRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        var university = universityRepository.findById(request.universityId())
                                             .orElseThrow(() -> NoSuchUniversityException.withId(request.universityId()));

        var education = Education.builder()
                                 .university(university)
                                 .fieldOfStudy(request.fieldOfStudy())
                                 .title(request.title())
                                 .grade(request.grade())
                                 .description(request.description())
                                 .startDate(request.startDate())
                                 .endDate(request.endDate())
                                 .individual(user)
                                 .build();

        education = educationRepository.save(education);
        return EducationView.from(education);
    }

    public EducationView updateEducation(Long userId, Long educationId, UpdateEducationRequest request) {
        var education = educationRepository.findByIdAndUserId(educationId, userId)
                                           .orElseThrow(() -> EducationNotFoundException.withId(educationId));

        updateUniversity(request.universityId(), education::setUniversity);
        updateIfPresent(request.fieldOfStudy(), education::setFieldOfStudy);
        updateIfPresent(request.title(), education::setTitle);
        updateIfPresent(request.grade(), education::setGrade);
        updateIfPresent(request.description(), education::setDescription);
        updateIfPresent(request.startDate(), education::setStartDate);
        updateIfPresent(request.endDate(), education::setEndDate);

        education.setUpdatedAt(LocalDateTime.now());
        education = educationRepository.save(education);
        return EducationView.from(education);
    }

    public void deleteEducation(Long userId, Long educationId) {
        var education = educationRepository.findByIdAndUserId(educationId, userId)
                                           .orElseThrow(() -> EducationNotFoundException.withId(educationId));

        educationRepository.delete(education);
    }

    public List<EducationView> getEducations(Long userId) {
        return educationRepository.findAllByUserId(userId)
                                  .stream()
                                  .map(EducationView::from)
                                  .toList();
    }


    private void updateUniversity(JsonNullable<Long> universityIdNullable, Consumer<University> universitySetter) {
        if (!JsonNullableWrapper.isPresent(universityIdNullable)) {
            return;
        }

        var universityId = universityIdNullable.get();
        var university = universityRepository.findById(universityId)
                                             .orElseThrow(() -> NoSuchUniversityException.withId(universityId));

        universitySetter.accept(university);
    }
}
