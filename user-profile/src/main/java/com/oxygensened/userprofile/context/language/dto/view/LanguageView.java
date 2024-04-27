package com.oxygensened.userprofile.context.language.dto.view;

import com.oxygensened.userprofile.domain.entity.Language;

public record LanguageView(Long id,
                           String name,
                           String description) {

    public static LanguageView from(Language language) {
        return new LanguageView(language.id(), language.name(), language.description());
    }
}
