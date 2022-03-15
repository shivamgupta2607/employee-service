package com.shivam.employee.constants;

import com.shivam.employee.dto.filter.SearchOperation;

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

    public static final String BASE_PACKAGE = "com.shivam";
    public static final String ERROR_RESPONSE = "Error response:\\n{}";
    public static final String EXCEPTION_THROWN = "Exception is thrown: ";
    public static final String FAILED_INVALID_ARGUMENT = "Failed due to invalid arguments: ";
    public static final String FAILED_INVALID_METHOD_ARGUMENT =
            "Failed due to invalid method arguments.";
    public static final String FAILED_DATABASE_CONSTRAINT_VIOLATIONS =
            "Failed due to database constraint violations.";
    public static final String FAILED_DATABASE_INTEGRITY_VIOLATIONS =
            "Failed due to database data integrity violations.";
    public static final String FAILED_DATABASE_VIOLATIONS = "Failed due to database violations.";
    public static final String FAILED_EXCEPTION_PROCESSING_REQUEST =
            "Failed due to exception while processing the request. Error Message: %s";
}
