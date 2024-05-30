package com.oxygensened.userprofile.context.education;

import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensened.userprofile.context.education.dto.request.CreateEducationRequest;
import com.oxygensened.userprofile.context.education.dto.request.UpdateEducationRequest;
import com.oxygensened.userprofile.context.education.dto.view.EducationView;
import com.oxygensened.userprofile.context.utils.JsonNullableWrapper;
import com.oxygensened.userprofile.domain.entity.Education;
import com.oxygensened.userprofile.domain.entity.University;
import com.oxygensened.userprofile.domain.exception.EducationNotFoundException;
import com.oxygensened.userprofile.domain.exception.NoSuchUniversityException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.EducationRepository;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class EducationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EducationService.class);
    private final EducationRepository educationRepository;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;
    private final RequestContext requestContext;

    public EducationService(EducationRepository educationRepository, UniversityRepository universityRepository, UserRepository userRepository, RequestContext requestContext) {
        this.educationRepository = educationRepository;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
        this.requestContext = requestContext;
    }

    public EducationView createEducation(Long userId, CreateEducationRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to create education for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

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

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allowed to update education {}", requestContext.userIdAsString(), educationId);
            throw new AccessDeniedException();
        }

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
        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allowed to delete education {}", requestContext.userIdAsString(), educationId);
            throw new AccessDeniedException();
        }

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
