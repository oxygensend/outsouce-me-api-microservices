package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.JobPosition;
import java.util.List;
import java.util.Optional;

public interface JobPositionRepository {

    JobPosition save(JobPosition jobPosition);

    void delete(JobPosition jobPosition);

    Optional<JobPosition> findByIdAndIndividualId(Long id, Long userId);

    List<JobPosition> findAllByIndividualId(Long userId);
}
