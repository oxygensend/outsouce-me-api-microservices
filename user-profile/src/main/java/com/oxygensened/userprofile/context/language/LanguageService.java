package com.oxygensened.userprofile.context.language;

import com.oxygensened.userprofile.context.language.dto.CreateLanguageRequest;
import com.oxygensened.userprofile.context.language.dto.LanguageView;
import com.oxygensened.userprofile.context.language.dto.UpdateLanguageRequest;
import com.oxygensened.userprofile.domain.Language;
import com.oxygensened.userprofile.domain.LanguageRepository;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.LanguageNotFoundException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;

    public LanguageService(LanguageRepository languageRepository, UserRepository userRepository) {
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
    }

    public LanguageView createLanguage(Long userId, CreateLanguageRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));
        var language = new Language(user, request.name(), request.description());
        return LanguageView.from(languageRepository.save(language));
    }

    public LanguageView updateLanguage(Long userId, Long languageId, UpdateLanguageRequest request) {
        var language = languageRepository.findByIdAndUserId(languageId, userId)
                                         .orElseThrow(() -> LanguageNotFoundException.withId(languageId));

        updateIfPresent(request.name(), language::setName);
        updateIfPresent(request.description(), language::setDescription);

        return LanguageView.from(languageRepository.save(language));
    }

    public void deleteLanguage(Long userId, Long languageId) {
        var language = languageRepository.findByIdAndUserId(languageId, userId)
                                         .orElseThrow(() -> LanguageNotFoundException.withId(languageId));
        languageRepository.delete(language);
    }

    public List<LanguageView> getLanguages(Long userId) {
        return languageRepository.findAllByUserId(userId).stream()
                                 .map(LanguageView::from)
                                 .toList();
    }
}
