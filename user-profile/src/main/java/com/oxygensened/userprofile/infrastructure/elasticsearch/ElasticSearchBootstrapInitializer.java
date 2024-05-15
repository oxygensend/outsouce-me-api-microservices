package com.oxygensened.userprofile.infrastructure.elasticsearch;

import com.oxygensened.userprofile.domain.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ElasticSearchMapper elasticsearchMapper;
    private final Map<BootstrapType, Runnable> BOOTSTRAP_TYPE_MAP = Map.of(
            BootstrapType.REINDEX, this::reindex,
            BootstrapType.REFRESH, this::refresh
    );
    private final BootstrapType bootstrapType;

    ElasticSearchBootstrapInitializer(ElasticsearchOperations elasticsearchOperations, UserRepository userRepository, ElasticSearchMapper elasticsearchMapper, BootstrapType bootstrapType) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.userRepository = userRepository;
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
        elasticsearchOperations.indexOps(UserES.class).delete();
        elasticsearchOperations.indexOps(UserES.class).create();
        var users = userRepository.findAll();
        List<UserES> esUsers = new ArrayList<>();
        for (var user : users) {
            esUsers.add(elasticsearchMapper.mapUserToUserES(user));

        }
        elasticsearchOperations.save(esUsers.toArray());
        LOGGER.info("Completed Elasticsearch reindexing");
    }


    private void refresh() {
        LOGGER.info("Refreshing Elasticsearch");
        elasticsearchOperations.indexOps(UserES.class).refresh();
        var users = userRepository.findAll();
        List<UserES> esUsers = new ArrayList<>();

        for (var user : users) {
            UserES userES = elasticsearchOperations.get(user.id().toString(), UserES.class);
            if (userES == null) {
                elasticsearchOperations.get(user.id().toString(), UserES.class);
                esUsers.add(elasticsearchMapper.mapUserToUserES(user));
            }

        }
        elasticsearchOperations.save(esUsers.toArray());
        LOGGER.info("Completed Elasticsearch refresh");
    }
}

