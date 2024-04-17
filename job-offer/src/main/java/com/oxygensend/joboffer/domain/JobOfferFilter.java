package com.oxygensend.joboffer.domain;

import com.oxygensend.joboffer.context.JobOfferSort;
import java.util.List;

import static java.util.Collections.emptyList;

public record JobOfferFilter(List<WorkType> workTypes,
                             List<String> technologies,
                             List<FormOfEmployment> formOfEmployments,
                             String userId,
                             String postCode,
                             String city,
                             Boolean archived,
                             JobOfferSort sort) {


    public JobOfferFilter {
        workTypes = workTypes == null ? emptyList() : workTypes;
        technologies = technologies == null ? emptyList() : technologies;
        formOfEmployments = formOfEmployments == null ? emptyList() : formOfEmployments;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private List<WorkType> workTypes = emptyList();
        private List<String> technologies = emptyList();
        private List<FormOfEmployment> formOfEmployments = emptyList();
        private String userId;
        private String postCode;
        private String city;
        private Boolean archived = false;
        private JobOfferSort sort;

        public Builder workTypes(List<WorkType> workTypes) {
            this.workTypes = workTypes;
            return this;
        }

        public Builder technologies(List<String> technologies) {
            this.technologies = technologies;
            return this;
        }

        public Builder formOfEmployments(List<FormOfEmployment> formOfEmployments) {
            this.formOfEmployments = formOfEmployments;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder postCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder archived(Boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder sort(JobOfferSort sort) {
            this.sort = sort;
            return this;
        }

        public JobOfferFilter build() {
            return new JobOfferFilter(workTypes, technologies, formOfEmployments,  userId, postCode, city, archived, sort);
        }
    }


}
