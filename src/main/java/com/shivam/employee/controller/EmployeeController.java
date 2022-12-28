package com.shivam.employee.controller;

import com.shivam.employee.advice.Loggable;
import com.shivam.employee.constants.ApiConstants;
import com.shivam.employee.dto.filter.FilterCriteria;
import com.shivam.employee.dto.request.EmployeeRequest;
import com.shivam.employee.dto.response.EmployeeResponse;
import com.shivam.employee.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping(value = ApiConstants.EMPLOYEE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Loggable
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid final EmployeeRequest employeeRequest) {
        log.debug("Incoming request to create a new employee with name {}", employeeRequest.getName());
        EmployeeResponse employeeResponse = this.employeeService.create(employeeRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> searchEmployees(
            @RequestParam(name = ApiConstants.API_RP_SEARCH,
                    required = false) final String fullTextSearch,
            @RequestParam(name = ApiConstants.API_RP_QUERY, required = false) final String query,
            @RequestParam(name = ApiConstants.API_RP_CUSTOM_FIELD, required = false) final String customFields,
            @RequestParam(name = ApiConstants.API_RP_SORT,
                    defaultValue = ApiConstants.DEFAULT_SORT_COLUMN) final String sort,
            @RequestParam(name = ApiConstants.API_RP_SORT_DIR,
                    defaultValue = ApiConstants.DEFAULT_SORT_DIRECTION) final String sortDir,
            @RequestParam(name = ApiConstants.API_RP_PAGE,
                    defaultValue = ApiConstants.DEFAULT_PAGE) final int page,
            @RequestParam(name = ApiConstants.API_RP_LIMIT,
                    defaultValue = ApiConstants.DEFAULT_PAGE_LIMIT) final int limit) {

        final FilterCriteria filterCriteria =
                new FilterCriteria(fullTextSearch, query, page, limit, sort, sortDir);

        log.debug("Incoming request to fetch employees with filter {}", filterCriteria);

        return new ResponseEntity<>(
                employeeService.search(filterCriteria),
                HttpStatus.OK);
    }

    @GetMapping(ApiConstants.ENDPOINT_ID)
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable("id") final Long employeeId) {
        log.debug("Incoming request to fetch employee by id {}", employeeId);

        return new ResponseEntity<>(employeeService.getById(employeeId), HttpStatus.OK);
    }


}
