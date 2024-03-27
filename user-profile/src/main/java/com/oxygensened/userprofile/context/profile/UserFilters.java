package com.oxygensened.userprofile.context.profile;

import com.oxygensened.userprofile.context.profile.dto.UserOrder;
import com.oxygensened.userprofile.domain.AccountType;

public record UserFilters(AccountType accountType, Boolean lookingForJob, UserOrder order) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AccountType accountType;
        private Boolean lookingForJob;
        private UserOrder order;

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder lookingForJob(Boolean lookingForJob) {
            this.lookingForJob = lookingForJob;
            return this;
        }

        public Builder order(UserOrder order) {
            this.order = order;
            return this;
        }

        public UserFilters build() {
            return new UserFilters(accountType, lookingForJob, order);
        }
    }
}
