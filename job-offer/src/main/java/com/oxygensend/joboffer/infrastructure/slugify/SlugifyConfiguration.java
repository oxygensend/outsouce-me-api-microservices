package com.oxygensend.joboffer.infrastructure.slugify;

import com.github.slugify.Slugify;
import com.oxygensend.joboffer.context.slug.SlugService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifyConfiguration {


    @Bean
    SlugService slugService() {
        return new SlugifyService(slugify());
    }

    private Slugify slugify() {
        return Slugify.builder()
                      .lowerCase(true)
                      .build();
    }

}
