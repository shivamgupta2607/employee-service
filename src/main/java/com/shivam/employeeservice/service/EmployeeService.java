//package com.shivam.employeeservice.service;
//
//import com.shivam.employeeservice.dto.EmployeeDesignationCounts;
//import com.shivam.employeeservice.dto.EmployeeRequest;
//import com.shivam.employeeservice.dto.EmployeeResponse;
//import com.shivam.employeeservice.dto.filter.FilterCriteria;
//import org.springframework.data.domain.Page;
//
//import java.util.Optional;
//
//public interface EmployeeService {
//
//    Page<EmployeeResponse> search(final FilterCriteria filterCriteria);
//
//    EmployeeResponse getById(final Long id);
//
//    EmployeeResponse create(final EmployeeRequest EmployeeRequest);
//
//    EmployeeResponse update(final EmployeeRequest EmployeeRequest);
//
//    EmployeeDesignationCounts getEmployeeCountsByDesignation(final Optional<String> designation);
//
//    void delete(final Long id);
//}
