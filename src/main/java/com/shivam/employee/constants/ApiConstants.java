package com.shivam.employee.constants;

public class ApiConstants {

    public static final String API_VERSION = "v1";
    public static final String SEPARATOR = "/";
    public static final String SWAGGER_ENDPOINTS = SEPARATOR + API_VERSION + ".*";

    public static final String API_GET_LIST = "list";
    public static final String API_RP_SEARCH = "search";
    public static final String API_RP_QUERY = "query";
    public static final String API_RP_CUSTOM_FIELD = "customfield";
    public static final String API_RP_TYPE = "type";
    public static final String API_RP_SORT = "sort";
    public static final String API_RP_SORT_DIR = "sortDir";
    public static final String API_RP_PAGE = "page";
    public static final String API_RP_LIMIT = "limit";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SORT_COLUMN = "createdAt";
    public static final String DEFAULT_PAGE_LIMIT = "10";
    public static final String DEFAULT_SORT_DIRECTION = "desc";
    public static final String SORT_DIR_ALLOWABLE_VALUES = "asc,desc";
    public static final String COMMA = ",";
    public static final String PIPE = "|";
    public static final String SPACE = " ";

    public static final String ENDPOINT_ID = SEPARATOR + "{id}";


    public static final String EMPLOYEE_API = "/v1/employee";
    public static final String TEAM_API = "/v1/team";

}
