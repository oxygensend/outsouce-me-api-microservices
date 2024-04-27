package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Education;
import com.oxygensened.userprofile.domain.repository.EducationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class EducationJpaFacadeRepository implements EducationRepository {
    private final EducationJpaRepository educationJpaRepository;

    EducationJpaFacadeRepository(EducationJpaRepository educationJpaRepository) {
        this.educationJpaRepository = educationJpaRepository;
    }

    @Override
    public Optional<Education> findByIdAndUserId(Long id, Long userId) {
        return educationJpaRepository.findByIdAndIndividualId(id, userId);
    }

    @Override
    public List<Education> findAllByUserId(Long userId) {
        return educationJpaRepository.findByIndividualId(userId);
    }

    @Override
    public Education save(Education education) {
        return educationJpaRepository.save(education);
    }

    @Override
    public void delete(Education education) {
        educationJpaRepository.delete(education);
    }
}
