package com.shivam.employee.service;

import com.shivam.employee.dto.filter.FilterCriteria;
import com.shivam.employee.dto.request.EmployeeRequest;
import com.shivam.employee.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Page<EmployeeResponse> search(final FilterCriteria filterCriteria);

    EmployeeResponse getById(final Long id);

    EmployeeResponse create(final EmployeeRequest EmployeeRequest);

    EmployeeResponse createNew(final EmployeeRequest employeeRequest);

    void dummyTestMethod();
}
