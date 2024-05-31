package com.oxygensened.userprofile.application.technology;

import java.util.List;

public interface TechnologyRepository {

    List<String> getFeaturedTechnologies();

    List<String> getTechnologies();
}
