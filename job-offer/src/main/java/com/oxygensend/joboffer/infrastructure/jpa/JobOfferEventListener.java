//package com.oxygensend.joboffer.infrastructure.jpa;
//
//import com.oxygensend.joboffer.domain.entity.JobOffer;
//import com.oxygensend.joboffer.domain.event.DomainEventPublisher;
//import com.oxygensend.joboffer.domain.event.Event;
//import com.oxygensend.joboffer.domain.event.JobOfferDataEvent;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import org.hibernate.event.spi.PostInsertEvent;
//import org.hibernate.event.spi.PostInsertEventListener;
//import org.hibernate.event.spi.PostUpdateEvent;
//import org.hibernate.event.spi.PostUpdateEventListener;
//import org.hibernate.persister.entity.EntityPersister;
//import org.springframework.stereotype.Component;
//
//@Component
//final class JobOfferEventListener implements PostUpdateEventListener, PostInsertEventListener {
//    private static final List<String> listenToFields = List.of("experience", "technologies", "archived");
//    private final DomainEventPublisher domainEventPublisher;
//
//    JobOfferEventListener(DomainEventPublisher domainEventPublisher) {
//        this.domainEventPublisher = domainEventPublisher;
//    }
//
//    @Override
//    public void onPostInsert(PostInsertEvent event) {
//        if (event.getEntity() instanceof JobOffer jobOffer) {
//            domainEventPublisher.publish(new JobOfferDataEvent(jobOffer, Event.CREATE));
//        }
//    }
//
//    @Override
//    public boolean requiresPostCommitHandling(EntityPersister persister) {
//        return false;
//    }
//
//    @Override
//    public void onPostUpdate(PostUpdateEvent event) {
//        if (event.getEntity() instanceof JobOffer jobOffer) {
//
//            Object[] oldState = event.getOldState();
//            Object[] currentState = event.getState();
//            String[] propertyNames = event.getPersister().getPropertyNames();
//            Map<String, Object> changes = new HashMap<>();
//            for (int i = 0; i < oldState.length; i++) {
//                if (!Objects.equals(oldState[i], currentState[i]) && listenToFields.contains(propertyNames[i])) {
//                    changes.put(propertyNames[i], currentState[i]);
//                }
//            }
//
//            if (!changes.isEmpty()) {
//                domainEventPublisher.publish(new JobOfferDataEvent(jobOffer));
//            }
//        }
//    }
//}
