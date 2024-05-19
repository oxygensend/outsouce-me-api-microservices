package com.oxygensened.userprofile.domain.entity;

import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class JobOffer {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Experience experience;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies = new HashSet<>();

    @ManyToOne
    private Address address;

    public JobOffer(Long id, User user, Experience experience, Set<String> technologies, Address address) {
        this.id = id;
        this.user = user;
        this.experience = experience;
        this.technologies = technologies == null ? new HashSet<>() : technologies;
        this.address = address;
    }

    public JobOffer() {

    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User user() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Experience experience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public Address address() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
