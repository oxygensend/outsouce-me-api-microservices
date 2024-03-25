package com.oxygensened.userprofile.context.notifications;

public record NotificationEvent<C>(C content, String login, String serviceId) {
}
