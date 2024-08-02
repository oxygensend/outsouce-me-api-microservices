package com.oxygensened.userprofile.infrastructure.domain;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import com.oxygensened.userprofile.domain.service.DeveloperOrderService;
import com.oxygensened.userprofile.domain.service.DevelopersForYou;
import com.oxygensened.userprofile.infrastructure.cache.CacheNotAvailableException;
import com.oxygensened.userprofile.infrastructure.cache.qualifiers.ForYouCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class DevelopersForYouWithRedisCache implements DevelopersForYou {
    private static final String CACHE_NAME = "for-you-developers";

    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final DeveloperOrderService developerOrderService;

    DevelopersForYouWithRedisCache(UserRepository userRepository, @ForYouCache CacheManager cacheManager,
                                   DeveloperOrderService developerOrderService) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
        this.developerOrderService = developerOrderService;
    }

    @Override
    public Page<User> getForUser(Long userId, UserFilter filter, Pageable pageable) {
        var cache = getCache();
        var cachedDevelopers = cache.get(userId, User[].class);
        if (cachedDevelopers != null) {
            var filteredOffers = Arrays.stream(cachedDevelopers)
                                       .toList();
            return paginatedResults(filteredOffers, pageable);
        }

        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        List<User> allDevelopers = userRepository.findAllDevelopers();
        var sortedOffers = developerOrderService.sortDeveloperForYou(allDevelopers, user)
                                                .toList();
        cache.put(userId, sortedOffers.toArray(new User[0]));
        return paginatedResults(sortedOffers, pageable);
    }

    private Page<User> paginatedResults(List<User> sortedDevelopers, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedDevelopers.size());
        return new PageImpl<>(sortedDevelopers.subList(start, end), pageable, sortedDevelopers.size());
    }

    private Cache getCache() {
        var cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) {
            throw new CacheNotAvailableException(CACHE_NAME);
        }
        return cache;
    }
}
