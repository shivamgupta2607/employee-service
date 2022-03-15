package com.shivam.employee.repository.specification;

import com.shivam.employee.dto.filter.SearchCriteria;
import com.shivam.employee.entity.Team;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Product specification.
 */
public class TeamSpecification implements Specification<Team> {

    private static final String WILDCARD = "%";
    private final SearchCriteria criteria;

    public TeamSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Team> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return criteriaBuilder
                        .equal(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                                criteria.getValue().toString().toLowerCase());
            case NEGATION:
                return criteriaBuilder
                        .notEqual(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                                criteria.getValue().toString().toLowerCase());
            case GREATER_THAN:
                return criteriaBuilder
                        .greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder
                        .lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return criteriaBuilder
                        .like(criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)),
                                containsLowerCase(criteria.getValue().toString()));
            default:
                return null;
        }
    }

    private String containsLowerCase(final String searchField) {
        return WILDCARD + searchField.toLowerCase() + WILDCARD;
    }
}
