package com.oxygensened.userprofile.infrastructure.slug;

import com.github.slugify.Slugify;
import com.oxygensened.userprofile.domain.service.SlugService;
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
