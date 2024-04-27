package com.oxygensened.userprofile.context.education.university;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "University")
@RestController
@RequestMapping("/api/v1/users")
final class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/universities")
    List<UniversityView> getAllUniversities() {
        return universityService.getAllUniversities();
    }
}
