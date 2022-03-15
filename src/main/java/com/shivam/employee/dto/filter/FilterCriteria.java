package com.shivam.employee.dto.filter;

import com.shivam.employee.constants.AppConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter criteria.
 */
@Data
public class FilterCriteria {

    private final String fullTextSearch;
    private final String query;
    private final int page;
    private final int limit;
    private final String sort;
    private final String sortDir;
    private Date fromDate;
    private Date toDate;


    public FilterCriteria(String fullTextSearch, String query, int page, int limit,
                          String sort, String sortDir, Date fromDate, Date toDate) {
        this.fullTextSearch = fullTextSearch;
        this.query = query;
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.sortDir = sortDir;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public FilterCriteria(String fullTextSearch, String query, int page, int limit,
                          String sort, String sortDir) {
        this.fullTextSearch = fullTextSearch;
        this.query = query;
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.sortDir = sortDir;
    }


    public List<SearchCriteria> getCriterion() {
        if (!hasQuery()) {
            return Collections.emptyList();
        }

        final List<SearchCriteria> params = new ArrayList<>();
        final Pattern pattern = Pattern.compile(AppConstants.FILTER_CRITERIA_REGEX);
        final Matcher matcher = pattern.matcher(query + AppConstants.COMMA);

        while (matcher.find()) {
            params.add(
                    new SearchCriteria(matcher.group(1), SearchOperation.getOperation(matcher.group(2)),
                            matcher.group(3)));
        }
        return params;
    }

    public boolean hasQuery() {
        return StringUtils.isNotEmpty(query);
    }
}
