package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.University;
import com.oxygensened.userprofile.domain.UniversityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class UniversityJpaFacadeRepository implements UniversityRepository {

    private final UniversityJpaRepository universityJpaRepository;

    UniversityJpaFacadeRepository(UniversityJpaRepository universityJpaRepository) {
        this.universityJpaRepository = universityJpaRepository;
    }

    @Override
    public Optional<University> findById(Long id) {
        return universityJpaRepository.findById(id);
    }

    @Override
    public List<University> findAll() {
        return universityJpaRepository.findAll();
    }
}
