package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Education;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationJpaRepository extends JpaRepository<Education, Long> {

    Optional<Education> findByIdAndIndividualId(Long id, Long userId);

    List<Education> findByIndividualIdOrderByStartDateDesc(Long userId);
}
