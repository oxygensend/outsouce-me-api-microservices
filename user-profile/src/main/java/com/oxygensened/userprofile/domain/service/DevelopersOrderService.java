package com.oxygensened.userprofile.domain.service;

import com.oxygensened.userprofile.domain.entity.User;
import java.util.List;

public interface DevelopersOrderService {

    void calculateDevelopersPopularityRate(User user, List<String> featuredTechnologies);
}
