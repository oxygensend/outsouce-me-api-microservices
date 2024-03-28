package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.context.profile.UserFilters;
import com.oxygensened.userprofile.context.profile.dto.UserOrder;
import com.oxygensened.userprofile.domain.AccountType;
import com.oxygensened.userprofile.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;


final class UserSpecification implements Specification<User> {

    private final List<Predicate> predicates = new ArrayList<>();
    private final AccountType accountType;
    private final Boolean lookingForJob;
    private final UserOrder order;

    private UserSpecification(AccountType accountType, Boolean lookingForJob, UserOrder order) {
        this.accountType = accountType;
        this.lookingForJob = lookingForJob;
        this.order = order;
    }

    public static UserSpecification read(UserFilters userFilters) {
        return new UserSpecification(userFilters.accountType(), userFilters.lookingForJob(), userFilters.order());
    }


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        predicateOfNullable(accountType, value -> cb.equal(root.get("accountType"), value));
        predicateOfNullable(lookingForJob, value -> cb.equal(root.get("lookingForJob"), value));

        specifyOrder(query, cb, root);

        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private void predicateOfNullable(Object value, Function<Object, Predicate> predicate) {
        Optional.ofNullable(value).map(predicate).ifPresent(predicates::add);
    }

    private void specifyOrder(CriteriaQuery<?> query, CriteriaBuilder cb, Root<User> root) {
        if (order == null) {
            return;
        }

        switch (order) {
            case POPULAR -> query.orderBy(cb.desc(root.get("popularityOrder")));
            case NEWEST -> query.orderBy(cb.desc(root.get("createdAt")));
            case NORMAL -> query.orderBy(cb.desc(root.get("displayOrder")));
        }
    }
}