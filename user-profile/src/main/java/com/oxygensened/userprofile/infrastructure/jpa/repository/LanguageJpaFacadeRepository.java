package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Language;
import com.oxygensened.userprofile.domain.repository.LanguageRepository;
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
        return languageJpaRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
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
