package com.oxygensened.userprofile.infrastructure.slugify;

import com.github.slugify.Slugify;
import com.oxygensened.userprofile.context.profile.slug.SlugService;

final class SlugifyService implements SlugService {
    private final Slugify slugify;

    SlugifyService(Slugify slugify) {
        this.slugify = slugify;
    }

    @Override
    public String createSlug(String value) {
        return slugify.slugify(value);
    }
}
