package com.oxygensend.joboffer.context.application.dto;

import org.springframework.data.domain.Sort;

public record ApplicationFilter(String userId,
                                ApplicationSort sort,
                                Sort.Direction dir) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private ApplicationSort sort;
        private Sort.Direction dir;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder sort(ApplicationSort sort) {
            this.sort = sort;
            return this;
        }

        public Builder dir(Sort.Direction dir) {
            this.dir = dir;
            return this;
        }

        public ApplicationFilter build() {
            return new ApplicationFilter(userId, sort, dir);
        }
    }
}
