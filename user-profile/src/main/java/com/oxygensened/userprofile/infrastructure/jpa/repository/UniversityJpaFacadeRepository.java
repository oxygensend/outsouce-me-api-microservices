package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.University;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
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

    @Override
    public boolean existsByName(String name) {
        return universityJpaRepository.existsByName(name);
    }
}
