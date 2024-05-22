package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.JobOfferSearchResult;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.infrastructure.elasticsearch.ElasticSearchMapper;
import com.oxygensend.joboffer.infrastructure.elasticsearch.JobOfferES;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.Queries;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Component;

@Component
final class JobOfferJpaFacadeRepository implements JobOfferRepository {
    private final JobOfferJpaRepository jobOfferJpaRepository;
    private final EntityManager entityManager;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticSearchMapper elasticSearchMapper;

    JobOfferJpaFacadeRepository(JobOfferJpaRepository jobOfferJpaRepository, EntityManager entityManager, ElasticsearchOperations elasticsearchOperations, ElasticSearchMapper elasticSearchMapper) {
        this.jobOfferJpaRepository = jobOfferJpaRepository;
        this.entityManager = entityManager;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticSearchMapper = elasticSearchMapper;
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        return jobOfferJpaRepository.findById(id);
    }

    @Override
    public JobOffer save(JobOffer jobOffer) {
        var saveJobOffer = jobOfferJpaRepository.save(jobOffer);
        var jobOfferES = elasticSearchMapper.mapJobOfferToJobOfferES(saveJobOffer);

        if (saveJobOffer.archived()) {
            elasticsearchOperations.delete(jobOffer.id().toString(), JobOfferES.class);
        } else {
            elasticsearchOperations.save(jobOfferES);
        }

        return saveJobOffer;
    }

    @Override
    public void delete(JobOffer jobOffer) {
        jobOfferJpaRepository.delete(jobOffer);
        elasticsearchOperations.delete(jobOffer.id().toString(), JobOfferES.class);
    }

    @Override
    public Optional<JobOffer> findBySlug(String slug) {
        return jobOfferJpaRepository.findBySlug(slug);
    }

    @Override
    public long findTheNewestSlugVersion(String slug) {
        return jobOfferJpaRepository.findTheNewestSlugVersion(slug);
    }

    @Override
    public Page<JobOffer> findAll(JobOfferFilter filter, Pageable pageable) {
        var specification = JobOfferSpecifications.getPredicateForJobOfferQuery(filter);
        return JpaUtils.findPageable(entityManager, pageable, JobOffer.class, specification);
    }

    @Override
    public List<JobOffer> findAll(JobOfferFilter filter) {
        var specification = JobOfferSpecifications.getPredicateForJobOfferQuery(filter);
        return jobOfferJpaRepository.findAll(specification);
    }

    @Override
    public List<JobOffer> findExpiredJobOffers() {
        var specification = JobOfferSpecifications.getPredicateForExpiredJobOffers();
        return jobOfferJpaRepository.findAll(specification);
    }

    @Override
    public List<JobOffer> findAll() {
        return jobOfferJpaRepository.findAll();
    }

    @Override
    public Page<JobOfferSearchResult> search(String query, Pageable pageable) {
        var esQuery = new NativeQueryBuilder().withSourceFilter(FetchSourceFilter.of(new String[] {"id", "name", "description", "slug", "numberOfApplications"}, null))
                                              .withQuery(Queries.wrapperQueryAsQuery("{\"multi_match\": {\"query\": \"" + query + "\"}}"))
                                              .withPageable(pageable)
                                              .withSort(Sort.by(Sort.Direction.DESC, "popularityOrder"))
                                              .build();
        var searchHits = SearchHitSupport.searchPageFor(elasticsearchOperations.search(esQuery, JobOfferES.class), pageable);
        var content = searchHits.stream()
                                .map(SearchHitSupport::unwrapSearchHits)
                                .map(object -> elasticSearchMapper.mapJobOfferESToJobOfferSearchResult((JobOfferES) object))
                                .toList();

        return new PageImpl<>(content, pageable, searchHits.getTotalElements());
    }
}
