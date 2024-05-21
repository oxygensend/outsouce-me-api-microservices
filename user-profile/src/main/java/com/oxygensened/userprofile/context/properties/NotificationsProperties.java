package com.oxygensened.userprofile.context.properties;

public interface NotificationsProperties {

    MailMessageProperties emailVerificationEmail();

    MailMessageProperties passwordResetEmail();

    MailMessageProperties welcomeMessageEmail();

    InternalMessageProperties welcomeMessageInternal();
}
