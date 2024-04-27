package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.context.application.dto.ApplicationFilter;
import com.oxygensend.joboffer.domain.entity.Application;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import static com.oxygensend.joboffer.infrastructure.jpa.repository.SpecificationUtils.predicateOfNullable;

final class ApplicationSpecifications {
    private ApplicationSpecifications() {

    }

    public static Specification<Application> getPredicateForApplicationQuery(ApplicationFilter filter) {
        return ((root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            root.fetch("jobOffer", JoinType.INNER);
            predicates.add(cb.equal(root.get("deleted"), false));

            predicateOfNullable(predicates, filter.userId(), value -> cb.equal(root.get("user").get("id"), value));

            switch (filter.dir()) {
                case ASC -> query.orderBy(cb.asc(root.get(filter.sort().dbField())));
                case DESC -> query.orderBy(cb.desc(root.get(filter.sort().dbField())));
            }
            ;

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}
