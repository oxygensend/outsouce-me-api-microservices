package com.oxygensened.userprofile.infrastructure.services.joboffers.dto;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import java.util.HashSet;
import java.util.List;

public record JobOfferResponse(Long id, Experience experience, List<String> technologies, String lon, String lat) {

    public JobOffer toEntity(){
        return new JobOffer(id, experience, new HashSet<>(technologies), lon, lat);
    }
}
