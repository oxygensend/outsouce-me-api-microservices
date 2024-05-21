package com.oxygensend.messenger.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class MailMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2048)
    private String content;

    @ManyToOne
    private User recipient;
    @ManyToOne
    private User sender;
    private LocalDateTime createdAt = LocalDateTime.now();

    public MailMessage() {
    }

    public MailMessage(String subject, String content, User recipient, User sender) {
        this.subject = subject;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
    }

    public Long id() {
        return id;
    }

    public String subject() {
        return subject;
    }

    public String setSubject(String subject) {
        this.subject = subject;
        return subject;
    }

    public String content() {
        return content;
    }

    public String setContent(String content) {
        this.content = content;
        return content;
    }

    public User recipient() {
        return recipient;
    }

    public User setRecipient(User recipient) {
        this.recipient = recipient;
        return recipient;
    }

    public User sender() {
        return sender;
    }

    public User setSender(User sender) {
        this.sender = sender;
        return sender;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "MailMessage{id=" + id + ", subject=" + subject + ", content=" + content + ", recipient=" + recipient + ", sender=" + sender + "}";
    }
}
