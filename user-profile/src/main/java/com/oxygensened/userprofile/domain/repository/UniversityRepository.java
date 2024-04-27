package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.University;
import java.util.List;
import java.util.Optional;

public interface UniversityRepository {
    Optional<University> findById(Long id);
    List<University> findAll();
}
