package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.Language;
import com.oxygensened.userprofile.domain.LanguageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class LanguageJpaFacadeRepository implements LanguageRepository {
    private final LanguageJpaRepository languageJpaRepository;

    LanguageJpaFacadeRepository(LanguageJpaRepository languageJpaRepository) {
        this.languageJpaRepository = languageJpaRepository;
    }

    @Override
    public Optional<Language> findByIdAndUserId(Long id, Long userId) {
        return languageJpaRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Language> findAllByUserId(Long userId) {
        return languageJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Language save(Language language) {
        return languageJpaRepository.save(language);
    }

    @Override
    public void delete(Language language) {
        languageJpaRepository.delete(language);
    }
}
