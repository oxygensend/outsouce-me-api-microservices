package com.oxygensened.userprofile.application.properties;

public interface NotificationsProperties {

    MailMessageProperties emailVerificationEmail();

    MailMessageProperties passwordResetEmail();

    MailMessageProperties welcomeMessageEmail();

    InternalMessageProperties welcomeMessageInternal();
}
