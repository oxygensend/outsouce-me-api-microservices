package com.oxygensened.userprofile.infrastructure.services.joboffers;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.JobOfferRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class JobOfferDataEventConsumer {
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    private final Map<Event, Consumer<JobOfferDataEvent>> eventHandlers = Map.of(
            Event.CREATE, this::onCreateEvent,
            Event.UPDATE, this::onUpdateEvent,
            Event.DELETE, this::onDeleteEvent
    );

    JobOfferDataEventConsumer(JobOfferRepository jobOfferRepository, UserRepository userRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
    }


    @KafkaListener(id = "jobOfferData", topics = "${services.job-offers.jobOfferDataTopic}",
            containerFactory = "jobOfferDataEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, JobOfferDataEvent> record) {
        var event = record.value();
        eventHandlers.get(event.event()).accept(event);
    }

    private void onDeleteEvent(JobOfferDataEvent event) {
        jobOfferRepository.deleteById(event.id());
    }

    private void onCreateEvent(JobOfferDataEvent event) {
        var user = userRepository.findById(Long.valueOf(event.userId()))
                                 .orElseThrow(() -> new UserNotFoundException("Event JobOfferData ignored, user %s not found"));
        jobOfferRepository.save(new JobOffer(event.id(), user, event.experience(), event.technologies()));
    }

    private void onUpdateEvent(JobOfferDataEvent event) {
        jobOfferRepository.findById(event.id()).ifPresentOrElse(jo -> {
            jo.setExperience(event.experience());
            jo.setTechnologies(event.technologies());
            jobOfferRepository.save(jo);
        }, () -> onCreateEvent(event));
    }

}
