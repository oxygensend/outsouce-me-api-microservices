package com.oxygensened.userprofile.domain;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository {
    Optional<Language> findByIdAndUserId(Long id, Long userId);

    List<Language> findAllByUserId(Long userId);

    Language save(Language language);

    void delete(Language language);
}