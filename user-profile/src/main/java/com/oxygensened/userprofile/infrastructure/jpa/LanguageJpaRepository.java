package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.Language;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface LanguageJpaRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByIdAndUserId(Long id, Long userId);

    List<Language> findAllByUserId(Long userId);
}
