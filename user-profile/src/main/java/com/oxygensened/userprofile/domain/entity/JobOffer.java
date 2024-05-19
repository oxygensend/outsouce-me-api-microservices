package com.oxygensened.userprofile.domain.entity;

import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Set;

@Entity
public class JobOffer {
    @Id
    private Long id;
    @ManyToOne
    private User user;
    private Experience experience;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies;

    public JobOffer(Long id, User user, Experience experience, Set<String> technologies) {
        this.id = id;
        this.user = user;
        this.experience = experience;
        this.technologies = technologies;
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
}
