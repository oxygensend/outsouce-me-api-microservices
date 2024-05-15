package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.UserSearchResult;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.repository.filters.UserFilters;
import com.oxygensened.userprofile.infrastructure.elasticsearch.ElasticSearchMapper;
import com.oxygensened.userprofile.infrastructure.elasticsearch.UserES;
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
import org.springframework.stereotype.Repository;

@Repository
class UserJpaFacadeRepository implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final EntityManager entityManager;
    private final ElasticSearchMapper elasticsearchMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    UserJpaFacadeRepository(UserJpaRepository userJpaRepository, EntityManager entityManager, ElasticSearchMapper elasticsearchMapper,
                            ElasticsearchOperations elasticsearchOperations) {
        this.userJpaRepository = userJpaRepository;
        this.entityManager = entityManager;
        this.elasticsearchMapper = elasticsearchMapper;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public User save(User user) {
        var savedUser = userJpaRepository.save(user);
        var userES = elasticsearchMapper.mapUserToUserES(savedUser);
        elasticsearchOperations.save(userES);
        return savedUser;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByExternalId(String externalId) {
        return userJpaRepository.findByExternalId(externalId);
    }

    @Override
    public Page<User> findAll(Pageable pageable, UserFilters filters) {
        var specification = UserSpecification.read(filters);
        return JpaUtils.findPageable(entityManager, pageable, User.class, specification);
    }

    @Override
    public long findTheNewestSlugVersion(String slug) {
        return userJpaRepository.findTheNewestSlugVersion(slug);
    }

    @Override
    public Optional<String> getThumbnail(Long userId) {
        return userJpaRepository.getThumbnail(userId);
    }

    @Override
    public Page<UserSearchResult> search(String query, Pageable pageable) {
        var esQuery = new NativeQueryBuilder().withSourceFilter(FetchSourceFilter.of(new String[] {"id", "fullName", "imagePath", "activeJobPosition"}, null))
                                              .withQuery(Queries.wrapperQueryAsQuery("{\"multi_match\": {\"query\": \"" + query + "\"}}"))
                                              .withPageable(pageable)
                                              .withSort(Sort.by(Sort.Direction.DESC, "popularityOrder"))
                                              .build();
        var searchHits = SearchHitSupport.searchPageFor(elasticsearchOperations.search(esQuery, UserES.class), pageable);
        var content = searchHits.stream()
                                .map(SearchHitSupport::unwrapSearchHits)
                                .map(object -> elasticsearchMapper.mapUserESToUserSearchResult((UserES) object))
                                .toList();

        return new PageImpl<>(content, pageable, searchHits.getTotalElements());
    }


    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }


}
