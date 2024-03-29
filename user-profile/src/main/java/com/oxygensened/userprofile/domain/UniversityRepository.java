package com.oxygensened.userprofile.domain;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository {
    Optional<University> findById(Long id);
    List<University> findAll();
}
