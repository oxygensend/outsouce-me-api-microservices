package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.Education;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationJpaRepository extends JpaRepository<Education, Long> {

    Optional<Education> findByIdAndIndividualId(Long id, Long userId);

    List<Education> findByIndividualId(Long userId);
}
