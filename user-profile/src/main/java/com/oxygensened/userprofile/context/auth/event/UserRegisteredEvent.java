package com.oxygensened.userprofile.context.auth.event;

public record UserRegisteredEvent(String userId, String businessId, String email, AccountActivation accountActivation) {

    public enum AccountActivation {
        VERIFY_EMAIL, CHANGE_PASSWORD, NONE
    }
}
