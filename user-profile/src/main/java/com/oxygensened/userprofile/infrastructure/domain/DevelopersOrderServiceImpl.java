package com.oxygensened.userprofile.infrastructure.domain;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.service.DevelopersOrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
class DevelopersOrderServiceImpl implements DevelopersOrderService {

    public void calculateDevelopersPopularityRate(User user, List<String> featuredTechnologies) {
        double randomRate = new Random().nextInt(100, 10000);

        List<String> userTechnologies = new ArrayList<>();
        List<String> userFeaturedTechnologies = new ArrayList<>();

        user.technologies().forEach(technology -> {
            if (featuredTechnologies.contains(technology)) {
                userFeaturedTechnologies.add(technology);
            } else {
                userTechnologies.add(technology);
            }
        });

        if (!userTechnologies.isEmpty()) {
            var proportionOfFeaturedTechnologies = userFeaturedTechnologies.size() / userTechnologies.size();
            randomRate *= (1 + proportionOfFeaturedTechnologies);
        }

        // REDIRECTS
        var redirects = user.redirectCount();
        randomRate *= (1 + (redirects > 1000 ? (double) redirects / 10000 : (double) redirects / 1000));

        // OPINIONS
        int opinionsCount = user.opinionsCount();
        if (opinionsCount > 0) {
            randomRate *= (1 + user.opinionsRate() * opinionsCount / 100);
        }

        user.setPopularityOrder((int) randomRate);
    }
}
