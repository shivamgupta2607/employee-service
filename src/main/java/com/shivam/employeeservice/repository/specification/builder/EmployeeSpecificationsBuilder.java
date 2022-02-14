package com.shivam.employeeservice.repository.specification.builder;

import com.shivam.employeeservice.constants.AppConstants;
import com.shivam.employeeservice.constants.EntityConstants;
import com.shivam.employeeservice.dto.filter.SearchCriteria;
import com.shivam.employeeservice.dto.filter.SearchOperation;
import com.shivam.employeeservice.entity.Employee;
import com.shivam.employeeservice.entity.Team;
import com.shivam.employeeservice.repository.specification.EmployeeSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class EmployeeSpecificationsBuilder {

    private final String fullTextSearch;
    private final List<SearchCriteria> params;
    private final List<Long> TeamIds;
    private final Integer fiscalYear;

    public EmployeeSpecificationsBuilder(final String fullTextSearch,
                                         final List<SearchCriteria> params, final List<Long> TeamIds, Integer fiscalYear) {
        this.fullTextSearch = fullTextSearch;
        this.params = params;
        this.TeamIds = TeamIds;
        this.fiscalYear = fiscalYear;
    }

    public static Specification<Employee> getTeamFieldSearchSpec(final String value,
                                                                 final String parentField, final String childField) {
        return (root, query, criteriaBuilder) -> {
            Join<Employee, Team> TeamJoin = root
                    .join(EntityConstants.TEAM);
            Predicate equalPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(TeamJoin.get(childField)), getLikeValueField(value));
            query.distinct(true);
            return equalPredicate;
        };
    }

    private static String getLikeValueField(final String value) {
        return new StringBuilder().append(AppConstants.LIKE_WILD_CARD).append(value.toLowerCase())
                .append(AppConstants.LIKE_WILD_CARD)
                .toString();
    }


    private Specification<Employee> getTeamIdSpec(List<Long> TeamIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Employee, Team> TeamJoin = root.join(EntityConstants.TEAM);
            In<Long> inClause = criteriaBuilder.in(TeamJoin.get(EntityConstants.ID));
            for (Long id : TeamIds) {
                inClause.value(id);
            }
            return inClause;
        };
    }

    public Specification<Employee> build() {
        Specification<Employee> spec = Specification.where(
                (getAttributeSpecificationForBool(EntityConstants.DELETED_FLG, Boolean.FALSE)));
        for (SearchCriteria criteria : params) {
            final String key = criteria.getKey();
            if (key.contains(AppConstants.CRITERIA_FIELD_DELIMITER)) {
                final int index = key.lastIndexOf(AppConstants.CRITERIA_FIELD_DELIMITER);
                spec = spec.and(
                        getTeamFieldSearchSpec(criteria.getValue().toString(), key.substring(0, index),
                                key.substring(index + 1)));
            } else {
                spec = spec.and(new EmployeeSpecification(criteria));
            }
        }
        if (!CollectionUtils.isEmpty(TeamIds)) {
            spec = spec.and(getTeamIdSpec(TeamIds));
        }

        spec = spec.and(buildFullTextSearchSpecification());
        return spec;
    }


    public Specification<Employee> buildFullTextSearchSpecification() {
        if (StringUtils.isNotEmpty(fullTextSearch)) {
            return Specification
                    .where(
                            (new EmployeeSpecification(
                                    new SearchCriteria(EntityConstants.NAME, SearchOperation.LIKE,
                                            fullTextSearch)))
                                    .or(getTeamFieldSearchSpec(fullTextSearch, EntityConstants.TEAM,
                                            EntityConstants.NAME))
                    );
        }

        return null;
    }

    public Specification<Employee> getAttributeSpecification(final String attribute,
                                                             final Object value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute).as(String.class), String.valueOf(value));
    }

    public Specification<Employee> getAttributeSpecificationForLong(final String attribute,
                                                                    final Long value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute), value);
    }

    public Specification<Employee> getAttributeSpecificationForBool(final String attribute,
                                                                    final Boolean value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute), value);
    }

}
