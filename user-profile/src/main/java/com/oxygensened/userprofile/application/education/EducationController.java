package com.oxygensened.userprofile.application.education;

import com.oxygensened.userprofile.application.cache.CacheData;
import com.oxygensened.userprofile.application.education.dto.request.CreateEducationRequest;
import com.oxygensened.userprofile.application.education.dto.view.EducationView;
import com.oxygensened.userprofile.application.education.dto.request.UpdateEducationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Education")
@CrossOrigin
@RequestMapping("/api/v1/users")
@RestController
class EducationController {
    private final EducationService educationService;

    EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping("/{userId}/educations")
    EducationView create(@PathVariable Long userId,
                         @Validated @RequestBody CreateEducationRequest request) {
        return educationService.createEducation(userId, request);
    }

    @PatchMapping("/{userId}/educations/{educationId}")
    EducationView update(@PathVariable Long userId,
                         @PathVariable Long educationId,
                         @Validated @RequestBody UpdateEducationRequest request) {
        return educationService.updateEducation(userId, educationId, request);
    }

    @DeleteMapping("/{userId}/educations/{educationId}")
    void delete(@PathVariable Long userId, @PathVariable Long educationId) {
        educationService.deleteEducation(userId, educationId);
    }

    @Cacheable(value = CacheData.EDUCATION_CACHE, key = "#userId")
    @GetMapping("/{userId}/educations")
    public List<EducationView> list(@PathVariable Long userId) {
        return educationService.getEducations(userId);
    }
}
