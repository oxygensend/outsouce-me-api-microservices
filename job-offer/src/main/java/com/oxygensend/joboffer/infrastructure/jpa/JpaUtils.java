package com.oxygensend.joboffer.infrastructure.jpa;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

final class JpaUtils {
    private JpaUtils() {
    }

    static <T> Page<T> findPageable(EntityManager entityManager, Pageable pageable, Class<T> clazz) {
        return findPageable(entityManager, pageable, clazz, null);
    }

    /*
     * This method is used to find pageable data with specification. Implementation is provided due to fact that in Hibernate 6.*
     * it is not possible to use separated query for count and data fetching. Soo the method eg. Page<T> findAll(Pageable pageable, Specification<T> specification)
     * from JpaRepository is not working properly with Hibernate 6.*.
     */
    static <T> Page<T> findPageable(EntityManager entityManager, Pageable pageable, Class<T> clazz, Specification<T> specification) {
        Session session = entityManager.unwrap(Session.class);
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<T> mainQuery = cb.createQuery(clazz);
        JpaRoot<T> root = mainQuery.from(clazz);

        if (specification != null) {
            mainQuery.where(specification.toPredicate(root, mainQuery, cb));
        }

        List<T> resultList = session.createQuery(mainQuery)
                                    .setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();

        Long count = session.createQuery(mainQuery.createCountQuery()).getSingleResult();

        return new PageImpl<>(resultList, pageable, count);
    }

}
