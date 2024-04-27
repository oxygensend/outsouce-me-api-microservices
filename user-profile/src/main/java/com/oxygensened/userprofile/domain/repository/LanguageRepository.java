package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.Language;
import java.util.List;
import java.util.Optional;

public interface LanguageRepository {
    Optional<Language> findByIdAndUserId(Long id, Long userId);

    List<Language> findAllByUserId(Long userId);

    Language save(Language language);

    void delete(Language language);
}
