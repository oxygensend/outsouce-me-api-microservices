package com.oxygensened.userprofile.domain.repository.filters;

import com.oxygensened.userprofile.domain.entity.part.AccountType;

public record UserFilter(AccountType accountType, Boolean lookingForJob, UserSort order) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AccountType accountType;
        private Boolean lookingForJob;
        private UserSort order;

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder lookingForJob(Boolean lookingForJob) {
            this.lookingForJob = lookingForJob;
            return this;
        }

        public Builder order(UserSort order) {
            this.order = order;
            return this;
        }

        public UserFilter build() {
            return new UserFilter(accountType, lookingForJob, order);
        }
    }
}
