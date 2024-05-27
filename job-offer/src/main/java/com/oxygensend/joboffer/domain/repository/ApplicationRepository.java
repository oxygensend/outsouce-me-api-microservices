package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.repository.filter.ApplicationFilter;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationRepository {
    Optional<Application> findById(Long id);

    boolean existsByJobOfferAndUser(JobOffer jobOffer, User user);

    Application save(Application application);

    Page<Application> findAll(ApplicationFilter filter, Pageable pageable);

    long count();

    void saveAll(Iterable<Application> applications);
}
