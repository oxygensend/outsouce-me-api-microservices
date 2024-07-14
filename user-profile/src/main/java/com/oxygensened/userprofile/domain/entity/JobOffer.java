package com.oxygensened.userprofile.domain.entity;

import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

public record JobOffer(Long id, Experience experience, Set<String> technologies, String lon, String lat) {
}
