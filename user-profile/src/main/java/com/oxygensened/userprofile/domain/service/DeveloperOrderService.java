package com.oxygensened.userprofile.domain.service;

import com.oxygensened.userprofile.domain.entity.User;
import java.util.List;
import java.util.stream.Stream;

public interface DeveloperOrderService {

    void calculateDevelopersPopularityRate(User user, List<String> featuredTechnologies);

    Stream<User> sortDeveloperForYou(List<User> developers, User user);
}
