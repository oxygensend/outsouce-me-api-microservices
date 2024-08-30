package com.oxygensend.joboffer.domain.repository.filter;

import static java.util.Collections.emptyList;

import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;

import java.util.ArrayList;
import java.util.List;

public record JobOfferFilter(List<WorkType> workTypes,
                             List<String> technologies,
                             List<FormOfEmployment> formOfEmployments,
                             String userId,
                             String postCode,
                             String city,
                             Boolean archived,
                             JobOfferSort sort,
                             String search) implements Filter {

    private static final List<Filter> FILTERS = new ArrayList<>();

    public JobOfferFilter {
        workTypes = workTypes == null ? emptyList() : workTypes;
        technologies = technologies == null ? emptyList() : technologies;
        formOfEmployments = formOfEmployments == null ? emptyList() : formOfEmployments;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean match(Object object) {
        return FILTERS.stream().allMatch(filter -> filter.match(object));
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
        private String search;

        public Builder workTypes(List<WorkType> workTypes) {
            if (workTypes != null) {
                FILTERS.add(new InFilter<>(workTypes));
            }
            this.workTypes = workTypes;
            return this;
        }

        public Builder technologies(List<String> technologies) {
            if (technologies != null) {
                FILTERS.add(new InFilter<>(technologies));
            }
            this.technologies = technologies;
            return this;
        }

        public Builder formOfEmployments(List<FormOfEmployment> formOfEmployments) {
            if (formOfEmployments != null) {
                FILTERS.add(new InFilter<>(formOfEmployments));
            }
            this.formOfEmployments = formOfEmployments;
            return this;
        }

        public Builder userId(String userId) {
            if (userId != null) {
                FILTERS.add(new EqualFilter(userId));
            }
            this.userId = userId;
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

        public Builder archived(Boolean archived) {
            if (archived != null) {
                FILTERS.add(new EqualFilter(archived));
            }
            this.archived = archived;
            return this;
        }

        public Builder sort(JobOfferSort sort) {
            this.sort = sort;
            return this;
        }

        // TODO include both fields in FILTERS
        public Builder search(String search) {
            this.search = search;
            return this;
        }

        public JobOfferFilter build() {
            return new JobOfferFilter(workTypes, technologies, formOfEmployments, userId, postCode, city, archived,
                                      sort, search);
        }
    }


}
