package com.oxygensened.userprofile.domain.repository.filters;

import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import java.util.ArrayList;
import java.util.List;

public record UserFilter(AccountType accountType,
                         Boolean lookingForJob,
                         UserSort sort,
                         String postCode,
                         String city,
                         Experience experience,
                         List<String> technologies) implements Filter {

    private static final List<Filter> FILTERS = new ArrayList<>();

    public UserFilter {
        technologies = technologies == null ? List.of() : technologies;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean match(Object object) {
        return FILTERS.stream().allMatch(filter -> filter.match(object));
    }

    public static class Builder {
        private AccountType accountType;
        private Boolean lookingForJob;
        private UserSort sort;
        private String postCode;
        private String city;
        private Experience experience;
        private List<String> technologies;

        public Builder accountType(AccountType accountType) {
            if (accountType != null) {
                FILTERS.add(new EqualFilter(accountType));
            }
            this.accountType = accountType;
            return this;
        }

        public Builder lookingForJob(Boolean lookingForJob) {
            if (lookingForJob != null) {
                FILTERS.add(new EqualFilter(lookingForJob));
            }
            this.lookingForJob = lookingForJob;
            return this;
        }

        public Builder sort(UserSort sort) {
            this.sort = sort;
            return this;
        }

        public Builder postCode(String postCode) {
            if (postCode != null) {
                FILTERS.add(new EqualFilter(postCode));
            }
            this.postCode = postCode;
            return this;
        }

        public Builder city(String city) {
            if (city != null) {
                FILTERS.add(new EqualFilter(city));
            }
            this.city = city;
            return this;
        }

        public Builder experience(Experience experience) {
            if (experience != null) {
                FILTERS.add(new EqualFilter(experience));
            }
            this.experience = experience;
            return this;
        }

        public Builder technologies(List<String> technologies) {
            if (technologies != null) {
                FILTERS.add(new InFilter<>(technologies));
            }
            this.technologies = technologies;
            return this;
        }

        public UserFilter build() {
            return new UserFilter(accountType, lookingForJob, sort, postCode, city, experience, technologies);
        }
    }
}
