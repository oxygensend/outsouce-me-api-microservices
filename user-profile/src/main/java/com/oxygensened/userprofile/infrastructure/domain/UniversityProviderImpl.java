package com.oxygensened.userprofile.infrastructure.domain;

import com.oxygensened.userprofile.context.UserProfileProperties;
import com.oxygensened.userprofile.domain.service.UniversityProvider;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
final class UniversityProviderImpl implements UniversityProvider {
    private final RestClient restClient;
    private final String plUniversitiesSourceUrl;

    UniversityProviderImpl(RestClient restClient, UserProfileProperties properties) {
        this.restClient = restClient;
        this.plUniversitiesSourceUrl = properties.plUniversitiesSourceUrl();
    }


    public List<String> getPolishUniversities() {
        PolishUniDto polishUniDto = restClient.get()
                                              .uri(plUniversitiesSourceUrl)
                                              .retrieve()
                                              .body(PolishUniDto.class);

        return Optional.ofNullable(polishUniDto)
                       .map(PolishUniDto::institutions)
                       .orElse(Collections.emptyList())
                       .stream()
                       .map(PolishUniDto.Institution::name)
                       .toList();
    }
}
