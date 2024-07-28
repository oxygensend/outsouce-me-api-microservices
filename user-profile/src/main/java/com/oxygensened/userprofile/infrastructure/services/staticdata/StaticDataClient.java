package com.oxygensened.userprofile.infrastructure.services.staticdata;

import com.oxygensened.userprofile.application.technology.dto.TechnologyDto;
import com.oxygensened.userprofile.infrastructure.services.staticdata.dto.AddressDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface StaticDataClient {

    @GetMapping("/api/v1/static-data/technologies")
    List<TechnologyDto> getTechnologies();

    @GetMapping("/api/v1/static-data/addresses/with-postal-codes")
    List<AddressDto> getAddresses();

}
