package com.shivam.employee.service;

import com.shivam.employee.dto.EmployeeRequest;
import com.shivam.employee.dto.EmployeeResponse;
import com.shivam.employee.dto.filter.FilterCriteria;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Page<EmployeeResponse> search(final FilterCriteria filterCriteria);

    EmployeeResponse getById(final Long id);

    EmployeeResponse create(final EmployeeRequest EmployeeRequest);
}
