package com.oxygensened.userprofile.domain;

import java.util.Optional;

public interface UniversityRepository {
    Optional<University> findById(Long id);
}
