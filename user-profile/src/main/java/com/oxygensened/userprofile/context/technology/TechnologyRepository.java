package com.oxygensened.userprofile.context.technology;

import java.util.List;

public interface TechnologyRepository {

    List<String> getFeaturedTechnologies();

    List<String> getTechnologies();
}
