package com.shivam.employeeservice.repository.specification.builder;


import com.shivam.employeeservice.constants.EntityConstants;
import com.shivam.employeeservice.dto.filter.SearchCriteria;
import com.shivam.employeeservice.dto.filter.SearchOperation;
import com.shivam.employeeservice.entity.Team;
import com.shivam.employeeservice.repository.specification.TeamSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Team specification builder
 */
public class TeamSpecificationsBuilder {

    private final String fullTextSearch;
    private final List<SearchCriteria> params;

    public TeamSpecificationsBuilder(final String fullTextSearch,
                                     final List<SearchCriteria> params) {
        this.fullTextSearch = fullTextSearch;
        this.params = params;
    }

    public Specification<Team> build() {
        Specification<Team> spec = Specification.where(
                getAttributeSpecificationForBool(EntityConstants.DELETED_FLG, Boolean.FALSE));

        for (SearchCriteria criteria : params) {
            spec = spec.and(new TeamSpecification(criteria));
        }
        spec = spec.and(buildFullTextSearchSpecification());
        return spec;
    }

    public Specification<Team> buildFullTextSearchSpecification() {
        if (StringUtils.isNotEmpty(fullTextSearch)) {
            return Specification.where(new TeamSpecification(
                    new SearchCriteria(EntityConstants.NAME, SearchOperation.LIKE, fullTextSearch)));
        }
        return null;
    }

    public Specification<Team> getAttributeSpecification(final String attribute,
                                                         final Object value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute).as(String.class), String.valueOf(value));
    }

    public Specification<Team> getAttributeSpecificationForLong(final String attribute,
                                                                final Long value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute), value);
    }

    public Specification<Team> getAttributeSpecificationForBool(final String attribute,
                                                                final Boolean value) {
        return (root, query, cb) -> cb
                .equal(root.get(attribute), value);
    }
}
