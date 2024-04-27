package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.JobPosition;
import com.oxygensened.userprofile.domain.repository.JobPositionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JobPositionJpaFacadeRepository implements JobPositionRepository {

    private final JobPositionJpaRepository jobPositionJpaRepository;

    JobPositionJpaFacadeRepository(JobPositionJpaRepository jobPositionJpaRepository) {
        this.jobPositionJpaRepository = jobPositionJpaRepository;
    }

    @Override
    public JobPosition save(JobPosition jobPosition) {
        return jobPositionJpaRepository.save(jobPosition);
    }

    @Override
    public void delete(JobPosition jobPosition) {
        jobPositionJpaRepository.delete(jobPosition);
    }

    @Override
    public Optional<JobPosition> findByIdAndIndividualId(Long id, Long userId) {
        return jobPositionJpaRepository.findByIdAndIndividualId(id, userId);
    }

    @Override
    public List<JobPosition> findAllByIndividualId(Long userId) {
        return jobPositionJpaRepository.findAllByIndividualId(userId);
    }
}
