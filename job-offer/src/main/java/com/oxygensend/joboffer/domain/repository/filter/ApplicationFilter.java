package com.oxygensend.joboffer.domain.repository.filter;

public record ApplicationFilter(String userId,
                                ApplicationSort sort,
                                SortDirection dir) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private ApplicationSort sort;
        private SortDirection dir;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder sort(ApplicationSort sort) {
            this.sort = sort;
            return this;
        }

        public Builder dir(SortDirection dir) {
            this.dir = dir;
            return this;
        }

        public ApplicationFilter build() {
            return new ApplicationFilter(userId, sort, dir);
        }
    }
}
