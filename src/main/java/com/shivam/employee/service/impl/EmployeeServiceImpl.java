package com.shivam.employee.service.impl;

import com.shivam.employee.advice.Loggable;
import com.shivam.employee.constants.EntityConstants;
import com.shivam.employee.dto.filter.FilterCriteria;
import com.shivam.employee.dto.filter.SearchCriteria;
import com.shivam.employee.dto.filter.SearchOperation;
import com.shivam.employee.dto.request.EmployeeRequest;
import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.dto.response.EmployeeResponse;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import com.shivam.employee.exception.RecordNotFoundException;
import com.shivam.employee.mapper.EmployeeMapper;
import com.shivam.employee.repository.EmployeeRepository;
import com.shivam.employee.repository.TeamRepository;
import com.shivam.employee.repository.specification.builder.EmployeeSpecificationsBuilder;
import com.shivam.employee.service.EmployeeService;
import com.shivam.employee.service.ExternalAPIService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ExternalAPIService externalAPIService;

    private final static EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    @Loggable
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Page<EmployeeResponse> search(final FilterCriteria filterCriteria) {
        List<SearchCriteria> params = filterCriteria.getCriterion();
        if (CollectionUtils.isEmpty(params)) {
            params = new ArrayList<>();
        }
        if (ObjectUtils.allNotNull(filterCriteria.getFromDate(), filterCriteria.getToDate())) {
            params.add(
                    new SearchCriteria(EntityConstants.JOINING_DATE, SearchOperation.GREATER_THAN_OR_EQUAL_TO,
                            filterCriteria.getFromDate()));
            params
                    .add(new SearchCriteria(EntityConstants.JOINING_DATE, SearchOperation.LESS_THAN_OR_EQUAL_TO,
                            filterCriteria.getToDate()));
        }
        final EmployeeSpecificationsBuilder builder = new EmployeeSpecificationsBuilder(
                filterCriteria.getFullTextSearch(), params);
        final Specification<Employee> specification = builder.build();
        final int page = filterCriteria.getPage();
        final int limit = filterCriteria.getLimit();
        final Pageable pageable = PageRequest
                .of(page, limit, Sort.by(Sort.Direction.fromString(filterCriteria.getSortDir()),
                        filterCriteria.getSort()));
        final Page<Employee> pagedEmployee = this.employeeRepository
                .findAll(specification, pageable);
        final List<Employee> employees = pagedEmployee.getContent();
        final List<EmployeeResponse> employeeResponseList = employeeMapper
                .mapToEmployeeResponse(employees);
        return new PageImpl<>(employeeResponseList, pagedEmployee.getPageable(),
                pagedEmployee.getTotalElements());
    }

    @Override
    @Loggable
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public EmployeeResponse getById(final Long id) {
        log.debug("Getting employee by id {}", id);
        final Optional<Employee> employeeOptional = this.employeeRepository
                .findByIdAndDeletedFalse(id);
        if (!employeeOptional.isPresent()) {
            throw new RecordNotFoundException(String.format("Employee by id %s not found", id));
        }
        final Employee employee = employeeOptional.get();
        final EmployeeResponse employeeResponse = employeeMapper.mapToEmployeeResponse(employee);
        return employeeResponse;
    }

    @Override
    @Loggable
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public EmployeeResponse create(final EmployeeRequest employeeRequest) {
        final Employee employee = employeeMapper.mapToEmployeeCreate(employeeRequest);
        log.debug("Employee is :  {} ", employee);
        employee.setCode(String.format("EMP-%s", getRandomString()));
        Optional<Team> optionalTeam = this.teamRepository.findByIdAndDeletedFalse(employeeRequest.getTeamId());
        if (!optionalTeam.isPresent()) {
            throw new RecordNotFoundException(String.format("Team with Id %s is not present", employeeRequest.getTeamId()));
        }
        employee.setTeam(optionalTeam.get());
        this.employeeRepository.save(employee);
        final UserRequest userRequest = new UserRequest();
        userRequest.setName(employee.getName());
        userRequest.setJob(employee.getDesignation().toString());
        this.externalAPIService.getTeamFromExternalAPI(userRequest);
        return employeeMapper.INSTANCE.mapToEmployeeResponse(employee);
    }

    private String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        final Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
