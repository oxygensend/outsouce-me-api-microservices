package com.oxygensend.joboffer.infrastructure.elasticsearch;

import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

final class ElasticSearchBootstrapInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchBootstrapInitializer.class);
    private final ElasticsearchOperations elasticsearchOperations;
    private final JobOfferRepository jobOfferRepository;
    private final ElasticSearchMapper elasticsearchMapper;
    private final Map<BootstrapType, Runnable> BOOTSTRAP_TYPE_MAP = Map.of(
            BootstrapType.REINDEX, this::reindex,
            BootstrapType.REFRESH, this::refresh
    );
    private final BootstrapType bootstrapType;

    ElasticSearchBootstrapInitializer(ElasticsearchOperations elasticsearchOperations, JobOfferRepository jobOfferRepository, ElasticSearchMapper elasticsearchMapper, BootstrapType bootstrapType) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.jobOfferRepository = jobOfferRepository;
        this.elasticsearchMapper = elasticsearchMapper;
        this.bootstrapType = bootstrapType;
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void initIndices() {
        LOGGER.info("Initializing Elasticsearch indices");

        var bootstrapStrategy = BOOTSTRAP_TYPE_MAP.get(bootstrapType);
        if (bootstrapStrategy == null) {
            LOGGER.info("No bootstrap strategy found for boostrap type {}", bootstrapType);
            return;
        }

        bootstrapStrategy.run();
    }

    private void reindex() {
        LOGGER.info("Reindexing Elasticsearch");
        elasticsearchOperations.indexOps(JobOfferES.class).delete();
        elasticsearchOperations.indexOps(JobOfferES.class).create();
        var jobOffers = jobOfferRepository.findAll();
        List<JobOfferES> esJobOffers = new ArrayList<>();
        for (var jobOffer : jobOffers) {
            esJobOffers.add(elasticsearchMapper.mapJobOfferToJobOfferES(jobOffer));
        }
        elasticsearchOperations.save(esJobOffers.toArray());
        LOGGER.info("Completed Elasticsearch reindexing");
    }


    private void refresh() {
        LOGGER.info("Refreshing Elasticsearch");
        elasticsearchOperations.indexOps(JobOfferES.class).refresh();
        var jobOffers = jobOfferRepository.findAll();
        List<JobOfferES> esJobOffers = new ArrayList<>();

        for (var jobOffer : jobOffers) {
            JobOfferES jobOfferES = elasticsearchOperations.get(jobOffer.id().toString(), JobOfferES.class);
            if (jobOfferES == null) {
                elasticsearchOperations.get(jobOffer.id().toString(), JobOfferES.class);
                esJobOffers.add(elasticsearchMapper.mapJobOfferToJobOfferES(jobOffer));
            }
        }
        elasticsearchOperations.save(esJobOffers.toArray());
        LOGGER.info("Completed Elasticsearch refresh");
    }
}
