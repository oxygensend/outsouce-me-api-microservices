package com.oxygensend.joboffer.domain.event;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import java.util.Set;

public record JobOfferDataEvent(Long id, String userId, Experience experience, Set<String> technologies, Event event) implements DomainEvent {

    public JobOfferDataEvent(Long id) {
        this(id, null, null, null, Event.DELETE);
    }

    public JobOfferDataEvent(JobOffer jobOffer) {
        this(jobOffer.id(), jobOffer.user().id(), jobOffer.experience(), jobOffer.technologies(), jobOffer.archived() ? Event.DELETE : Event.UPDATE);
    }

    public JobOfferDataEvent(JobOffer jobOffer, Event event) {
        this(jobOffer.id(), jobOffer.user().id(), jobOffer.experience(), jobOffer.technologies(), event);
    }

    @Override
    public String key() {
        return id.toString();
    }

    @Override
    public Topics topic() {
        return Topics.JOB_OFFER_DATA;
    }
}
