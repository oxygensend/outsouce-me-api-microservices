package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.Education;
import java.util.List;
import java.util.Optional;

public interface EducationRepository {
    Optional<Education> findByIdAndUserId(Long id, Long userId);

    List<Education> findAllByUserId(Long userId);

    Education save(Education education);

    void delete(Education education);

    void saveAll(List<Education> educations);

    long count();

}
