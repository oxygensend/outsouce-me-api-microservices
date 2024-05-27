package com.oxygensend.joboffer.application.technology;

import java.util.List;

public interface TechnologyRepository {

    List<String> getFeaturedTechnologies();
    List<String> getTechnologies();
}
