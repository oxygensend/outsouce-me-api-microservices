package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.repository.filter.ApplicationFilter;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
final class ApplicationJpaFacadeRepository implements ApplicationRepository {
    private final ApplicationJpaRepository applicationJpaRepository;
    private final EntityManager entityManager;

    ApplicationJpaFacadeRepository(ApplicationJpaRepository applicationJpaRepository, EntityManager entityManager) {
        this.applicationJpaRepository = applicationJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Application> findById(Long id) {
        return applicationJpaRepository.findById(id);
    }

    @Override
    public boolean existsByJobOfferAndUser(JobOffer jobOffer, User user) {
        return applicationJpaRepository.existsByJobOfferAndUser(jobOffer, user);
    }

    @Override
    public Application save(Application application) {
        return applicationJpaRepository.save(application);
    }

    @Override
    public Page<Application> findAll(ApplicationFilter filter, Pageable pageable) {
        var specification = ApplicationSpecifications.getPredicateForApplicationQuery(filter);
        return JpaUtils.findPageable(entityManager, pageable, Application.class, specification);
    }
}
