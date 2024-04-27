package com.oxygensend.joboffer.infrastructure.jpa.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class SpecificationUtils {

    public static void predicateOfNullable(List<Predicate> predicates, Object value, Function<Object, Predicate> predicate) {
        Optional.ofNullable(value).map(predicate).ifPresent(predicates::add);
    }

    public static void addInPredicate(List<Predicate> predicates, Path<Object> path, List<?> values) {
        if (!values.isEmpty()) {
            predicates.add(path.in(values));
        }
    }

    public static void addFindInSetPredicate(List<Predicate> predicates, CriteriaBuilder cb, Path<Object> path, Object objToFind) {
        var replaceExpression = cb.function("REPLACE", String.class, path, cb.literal(';'), cb.literal(","));
        var findInSetExpression = cb.function("FIND_IN_SET", Integer.class, cb.literal(objToFind), replaceExpression);
        predicates.add(cb.isNotNull(path));
        predicates.add(cb.greaterThan(findInSetExpression, 0));
    }
}
