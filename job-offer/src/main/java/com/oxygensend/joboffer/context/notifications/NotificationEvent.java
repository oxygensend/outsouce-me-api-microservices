package com.oxygensend.joboffer.context.notifications;

public record NotificationEvent<C>(C content, String login, String serviceId) {
}
