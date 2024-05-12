package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import static com.oxygensend.joboffer.infrastructure.jpa.repository.SpecificationUtils.addFindInSetPredicate;
import static com.oxygensend.joboffer.infrastructure.jpa.repository.SpecificationUtils.addInPredicate;
import static com.oxygensend.joboffer.infrastructure.jpa.repository.SpecificationUtils.predicateOfNullable;

final class JobOfferSpecifications {

    private JobOfferSpecifications() {
    }

    public static Specification<JobOffer> getPredicateForJobOfferQuery(JobOfferFilter filter) {
        return (root, query, cb) -> getPredicateForJobOfferQuery(root, query, cb, filter);
    }

    public static Specification<JobOffer> getPredicateForExpiredJobOffers() {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("validTo"), cb.currentTimestamp());
    }

    private static Predicate getPredicateForJobOfferQuery(Root<JobOffer> root, CriteriaQuery<?> query, CriteriaBuilder cb, JobOfferFilter filter) {
        final var predicates = new ArrayList<Predicate>();

        root.fetch("user", JoinType.INNER);

        if (filter.archived() != null) {
            predicates.add(cb.equal(root.get("archived"), filter.archived()));
        }

        predicateOfNullable(predicates, filter.userId(), value -> cb.equal(root.get("user").get("id"), value));

        if (filter.postCode() != null || filter.city() != null) {
            var addressJoin = root.join("address", JoinType.LEFT);
            predicateOfNullable(predicates, filter.postCode(), value -> cb.equal(addressJoin.get("postCode"), value));
            predicateOfNullable(predicates, filter.city(), value -> cb.equal(addressJoin.get("city"), value));
        }

        addInPredicate(predicates, root.get("formOfEmployment"), filter.formOfEmployments());


        for (var tech : filter.technologies()) {
            addFindInSetPredicate(predicates, cb, root.get("technologies"), tech);
        }

        for (var workType : filter.workTypes()) {
            addFindInSetPredicate(predicates, cb, root.get("workTypes"), workType.displayName());
        }

        switch (filter.sort()) {
            case POPULAR -> query.orderBy(cb.desc(root.get("popularityOrder")));
            case NEWEST -> query.orderBy(cb.desc(root.get("createdAt")));
            case FOR_YOU -> throw new UnsupportedOperationException("FOR_YOU sorting is not supported in jpa layer");
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


}
