package com.oxygensend.joboffer.infrastructure.domain;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.service.JobOfferOrderService;
import com.oxygensend.joboffer.domain.service.JobOffersForYou;
import com.oxygensend.joboffer.infrastructure.cache.CacheNotAvailableException;
import com.oxygensend.joboffer.infrastructure.cache.qualifiers.ForYouCache;
import java.util.Arrays;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
final class JobOffersForYouWithRedisCache implements JobOffersForYou {

    private static final String CACHE_NAME = "for-you-job-offers";
    private final JobOfferOrderService jobOfferOrderService;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    JobOffersForYouWithRedisCache(JobOfferOrderService jobOfferOrderService, JobOfferRepository jobOfferRepository, UserRepository userRepository,
                                  @ForYouCache CacheManager cacheManager) {
        this.jobOfferOrderService = jobOfferOrderService;
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }


    @Override
    public Page<JobOffer> getForUser(String userId, JobOfferFilter filter, Pageable pageable) {
        var cache = getCache();
        var cachedJobOffers = cache.get(userId, JobOffer[].class);
        if (cachedJobOffers != null) {
            var filteredOffers = Arrays.stream(cachedJobOffers)
                                       .filter(filter::match)
                                       .toList();
            return paginatedResults(filteredOffers, pageable);
        }

        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> NoSuchUserException.withId(userId));

        List<JobOffer> allOffers = jobOfferRepository.findAll();
        var sortedOffers = jobOfferOrderService.sortForYouJobOffers(allOffers, user)
                                               .filter(filter::match)
                                               .toList();
        cache.put(userId, sortedOffers.toArray(new JobOffer[0]));
        return paginatedResults(sortedOffers, pageable);
    }

    private Page<JobOffer> paginatedResults(List<JobOffer> sortedOffers, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedOffers.size());
        return new PageImpl<>(sortedOffers.subList(start, end), pageable, sortedOffers.size());
    }

    private Cache getCache() {
        var cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) {
            throw new CacheNotAvailableException(CACHE_NAME);
        }
        return cache;
    }
}
