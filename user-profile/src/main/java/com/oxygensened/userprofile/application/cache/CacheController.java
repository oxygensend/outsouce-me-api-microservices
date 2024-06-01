package com.oxygensened.userprofile.application.cache;

import com.oxygensened.userprofile.application.cache.event.ClearAllCacheEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/cache")
final class CacheController {
    private final CacheManagerComposite cacheManagerComposite;
    private final ApplicationEventPublisher applicationEventPublisher;

    CacheController(CacheManagerComposite cacheManagerComposite, ApplicationEventPublisher applicationEventPublisher) {
        this.cacheManagerComposite = cacheManagerComposite;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void clear(@RequestParam(name = "name", required = false) String name) {
        cacheManagerComposite.getCacheNames().stream()
                             .filter(cacheName -> name == null || cacheName.equals(name))
                             .forEach(cacheName -> applicationEventPublisher.publishEvent(new ClearAllCacheEvent(cacheName)));
    }
}
