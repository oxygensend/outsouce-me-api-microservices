package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import com.oxygensened.userprofile.domain.repository.filters.UserSort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import static com.oxygensened.userprofile.infrastructure.jpa.repository.SpecificationUtils.addFindInSetPredicate;
import static com.oxygensened.userprofile.infrastructure.jpa.repository.SpecificationUtils.predicateOfNullable;


final class UserSpecification {

    private UserSpecification() {

    }

    public static Specification<User> getPredicateForUsersQuery(UserFilter filter) {
        return (root, query, cb) -> getPredicateForUsersQuery(root, query, cb, filter);
    }

    private static Predicate getPredicateForUsersQuery(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb, UserFilter filter) {
        final var predicates = new ArrayList<Predicate>();

        predicateOfNullable(predicates, filter.accountType(), value -> cb.equal(root.get("accountType"), value));
        predicateOfNullable(predicates, filter.lookingForJob(), value -> cb.equal(root.get("lookingForJob"), value));
        predicateOfNullable(predicates, filter.experience(), value -> cb.equal(root.get("experience"), value));

        if (filter.postCode() != null || filter.city() != null) {
            var addressJoin = root.join("address", JoinType.LEFT);
            predicateOfNullable(predicates, filter.postCode(), value -> cb.equal(addressJoin.get("postCode"), value));
            predicateOfNullable(predicates, filter.city(), value -> cb.equal(addressJoin.get("city"), value));
        }

        for (var tech : filter.technologies()) {
            addFindInSetPredicate(predicates, cb, root.get("technologies"), tech);
        }

        specifyOrder(query, cb, root, filter.sort());

        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private static void specifyOrder(CriteriaQuery<?> query, CriteriaBuilder cb, Root<User> root, UserSort sort) {
        if (sort == null) {
            return;
        }

        switch (sort) {
            case POPULAR -> query.orderBy(cb.desc(root.get("popularityOrder")));
            case NEWEST -> query.orderBy(cb.desc(root.get("createdAt")));
            case FOR_YOU -> query.orderBy(cb.desc(root.get("displayOrder")));
        }
    }
}
