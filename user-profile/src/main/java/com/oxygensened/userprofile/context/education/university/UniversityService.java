package com.oxygensened.userprofile.context.education.university;

import com.oxygensened.userprofile.domain.UniversityRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<UniversityView> getAllUniversities() {
        return universityRepository.findAll()
                                   .stream()
                                   .map(UniversityView::from)
                                   .toList();
    }
}
