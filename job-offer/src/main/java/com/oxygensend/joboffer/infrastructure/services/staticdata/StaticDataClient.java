package com.oxygensend.joboffer.infrastructure.services.staticdata;

import com.oxygensend.joboffer.application.technology.dto.TechnologyDto;
import com.oxygensend.joboffer.infrastructure.services.staticdata.dto.AddressDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

public interface StaticDataClient {

    @GetMapping("/api/v1/static-data/technologies")
    List<TechnologyDto> getTechnologies();

    @GetMapping("/api/v1/static-data/addresses/with-postal-codes")
    List<AddressDto> getAddresses();
}
