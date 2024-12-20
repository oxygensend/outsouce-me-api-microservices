package com.oxygensend.joboffer.application.properties;

public interface NotificationsProperties {


    MailMessageProperties jobOfferApplicationEmail();

    InternalMessageProperties jobOfferApplicationInternalMessage();

    InternalMessageProperties jobOfferExpiredInternalMessageToPrincipal();

    MailMessageProperties jobOfferExpiredEmailToPrincipal();

    InternalMessageProperties jobOfferExpiredInternalMessageToAppliers();

    MailMessageProperties jobOfferExpiredEmailToAppliers();
}
