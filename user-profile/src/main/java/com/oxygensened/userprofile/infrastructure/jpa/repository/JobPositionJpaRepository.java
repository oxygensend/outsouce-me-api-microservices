package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.JobPosition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface JobPositionJpaRepository extends JpaRepository<JobPosition, Long> {

    Optional<JobPosition> findByIdAndIndividualId(Long id, Long userId);

    List<JobPosition> findAllByIndividualId(Long userId);
}
