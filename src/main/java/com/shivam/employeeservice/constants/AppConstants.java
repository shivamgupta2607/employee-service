package com.shivam.employeeservice.constants;

import com.shivam.employeeservice.dto.filter.SearchOperation;

public class AppConstants {

    public static final String COMMA = ",";
    public static final String PIPE = "|";
    public static final String WORDS_HYPHEN_SPACE_REGEX_KEY = "([\\w-. ]+)";
    public static final String WORDS_HYPHEN_SPACE_REGEX_VALUE = "([\\w-.()%$\\/\\\\ ]+)";
    public static final String FILTER_CRITERIA_REGEX =
            String.format(
                    "%s(%s)%s,",
                    WORDS_HYPHEN_SPACE_REGEX_KEY, SearchOperation.getOperationSet(), WORDS_HYPHEN_SPACE_REGEX_VALUE);
    public static final String ID = "id";
    public static final String CREATED_BY = "createdBy";
    public static final String UPDATED_BY = "updatedBy";
    public static final String UPDATED_AT = "updatedAt";
    public static final String CREATED_AT = "createdAt";
    public static final String DELETED = "deleted";
    public static final String ACTIVE = "active";


    public static final String CRITERIA_FIELD_DELIMITER = ".";
    public static final String LIKE_WILD_CARD = "%";

}
