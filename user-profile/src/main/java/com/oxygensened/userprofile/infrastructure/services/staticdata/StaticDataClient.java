package com.oxygensened.userprofile.infrastructure.services.staticdata;

import com.oxygensened.userprofile.context.technology.dto.TechnologyDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

public interface StaticDataClient {

    @GetMapping("/api/v1/static-data/technologies")
    List<TechnologyDto> getTechnologies();
}