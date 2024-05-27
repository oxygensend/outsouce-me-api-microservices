package com.oxygensend.joboffer.infrastructure.services.staticdata;

import com.oxygensend.joboffer.application.technology.TechnologyRepository;
import com.oxygensend.joboffer.application.technology.dto.TechnologyDto;
import java.util.List;

public class StaticDataTechnologyRepository implements TechnologyRepository {
    private final StaticDataClient staticDataClient;

    public StaticDataTechnologyRepository(StaticDataClient staticDataClient) {
        this.staticDataClient = staticDataClient;
    }

    public List<String> getFeaturedTechnologies() {
        return staticDataClient.getTechnologies().stream()
                               .filter(TechnologyDto::featured)
                               .map(TechnologyDto::name)
                               .toList();
    }

    @Override
    public List<String> getTechnologies() {
        return staticDataClient.getTechnologies().stream()
                               .map(TechnologyDto::name)
                               .toList();
    }
}
