package com.oxygensened.userprofile.domain;

public enum AccountType {
    Developer("ROLE_DEVELOPER"), Principle("ROLE_PRINCIPLE");

    private final String role;

    AccountType(String role) {
        this.role = role;
    }

    public String role() {
        return role;
    }
}
