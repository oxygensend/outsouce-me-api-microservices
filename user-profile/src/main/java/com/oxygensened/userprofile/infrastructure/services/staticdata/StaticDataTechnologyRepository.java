package com.oxygensened.userprofile.infrastructure.services.staticdata;

import com.oxygensened.userprofile.context.technology.TechnologyRepository;
import com.oxygensened.userprofile.context.technology.dto.TechnologyDto;
import java.util.List;

class StaticDataTechnologyRepository implements TechnologyRepository {
    private final StaticDataClient staticDataClient;

    StaticDataTechnologyRepository(StaticDataClient staticDataClient) {
        this.staticDataClient = staticDataClient;
    }

    public List<String> getFeaturedTechnologies() {
        return staticDataClient.getTechnologies().stream()
                               .filter(TechnologyDto::featured)
                               .map(TechnologyDto::name)
                               .toList();
    }

    public List<String> getTechnologies() {
        return staticDataClient.getTechnologies().stream()
                               .map(TechnologyDto::name)
                               .toList();
    }
}
