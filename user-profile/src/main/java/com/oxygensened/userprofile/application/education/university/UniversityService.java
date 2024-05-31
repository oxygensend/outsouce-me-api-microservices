package com.oxygensened.userprofile.application.education.university;

import com.oxygensened.userprofile.domain.entity.University;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
import com.oxygensened.userprofile.domain.service.UniversityProvider;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UniversityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityService.class);
    private final UniversityRepository universityRepository;
    private final UniversityProvider universityProvider;
    private final EntityManager entityManager;


    public UniversityService(UniversityRepository universityRepository, UniversityProvider universityProvider, EntityManager entityManager) {
        this.universityRepository = universityRepository;
        this.universityProvider = universityProvider;
        this.entityManager = entityManager;
    }

    public List<UniversityView> getAllUniversities() {
        return universityRepository.findAll()
                                   .stream()
                                   .map(UniversityView::from)
                                   .toList();
    }

    @Transactional
    public void downloadUniversities() {
        LOGGER.info("Started downloading universities from external sources");
        int i = 0;
        List<String> polishUniversities = universityProvider.getPolishUniversities();
        for (var uniName : polishUniversities) {
            if (!universityRepository.existsByName(uniName)) {
                var uni = new University(uniName);
                entityManager.persist(uni);
                i++;
            }
        }

        entityManager.flush();
        LOGGER.info("Successfully  downloaded %d new universities to database".formatted(i));
    }
}
