package com.oxygensened.userprofile.application.language;

import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensened.userprofile.application.cache.event.ClearCacheEvent;
import com.oxygensened.userprofile.application.cache.event.ClearListCacheEvent;
import com.oxygensened.userprofile.application.language.dto.request.CreateLanguageRequest;
import com.oxygensened.userprofile.application.language.dto.request.UpdateLanguageRequest;
import com.oxygensened.userprofile.application.language.dto.view.LanguageView;
import com.oxygensened.userprofile.domain.entity.Language;
import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensened.userprofile.domain.exception.LanguageNotFoundException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.LanguageRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.application.utils.Patch.updateIfPresent;

@Service
public class LanguageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageService.class);
    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final RequestContext requestContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    public LanguageService(LanguageRepository languageRepository, UserRepository userRepository, RequestContext requestContext, ApplicationEventPublisher applicationEventPublisher) {
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
        this.requestContext = requestContext;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public LanguageView createLanguage(Long userId, CreateLanguageRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to create language for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        var language = new Language(user, request.name(), request.description());
        applicationEventPublisher.publishEvent(ClearCacheEvent.language(userId));
        return LanguageView.from(languageRepository.save(language));
    }

    public LanguageView updateLanguage(Long userId, Long languageId, UpdateLanguageRequest request) {
        var language = languageRepository.findByIdAndUserId(languageId, userId)
                                         .orElseThrow(() -> LanguageNotFoundException.withId(languageId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to update language for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        updateIfPresent(request.name(), language::setName);
        updateIfPresent(request.description(), language::setDescription);

        applicationEventPublisher.publishEvent(ClearCacheEvent.language(userId));
        return LanguageView.from(languageRepository.save(language));
    }

    public void deleteLanguage(Long userId, Long languageId) {
        var language = languageRepository.findByIdAndUserId(languageId, userId)
                                         .orElseThrow(() -> LanguageNotFoundException.withId(languageId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to delete language for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        languageRepository.delete(language);
        applicationEventPublisher.publishEvent(ClearCacheEvent.language(userId));
    }

    public List<LanguageView> getLanguages(Long userId) {
        return languageRepository.findAllByUserId(userId).stream()
                                 .map(LanguageView::from)
                                 .toList();
    }
}
