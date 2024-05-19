package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import com.oxygensend.joboffer.infrastructure.jpa.converter.ApplicationStatusConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Application  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private JobOffer jobOffer;
    @Convert(converter = ApplicationStatusConverter.class)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    private String description;
    private boolean deleted = false;
    @OneToMany(mappedBy = "application", targetEntity = Attachment.class, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Attachment> attachments = new ArrayList<>();
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Application() {
    }

    public Application(User user, JobOffer jobOffer, String description) {
        this.user = user;
        this.jobOffer = jobOffer;
        this.description = description;
    }

    public Long id() {
        return id;
    }

    public User user() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobOffer jobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public ApplicationStatus status() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean deleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Attachment> attachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        if (!attachments.contains(attachment)) {
            attachments.add(attachment);
            attachment.setApplication(this);
        }
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
