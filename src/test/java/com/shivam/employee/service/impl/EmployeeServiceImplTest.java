package com.shivam.employee.service.impl;

import com.shivam.employee.dto.request.EmployeeRequest;
import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.dto.response.EmployeeResponse;
import com.shivam.employee.dto.response.UserResponse;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import com.shivam.employee.enums.Designation;
import com.shivam.employee.repository.EmployeeRepository;
import com.shivam.employee.repository.TeamRepository;
import com.shivam.employee.service.ExternalAPIService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ExternalAPIService externalAPIService;

    @Before
    public void setUp() {


    }

    @Test
    public void testGetById() {

        Employee employee = new Employee();
        employee.setName("A");
        employee.setId(1l);
        employee.setCode("EMP-001");

        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findByIdAndDeletedFalse(any())).thenReturn(optionalEmployee);
        EmployeeResponse employeeById = employeeService.getById(1l);
        Assert.assertNotNull(employeeById);
        Assert.assertNotNull(employeeById.getId());
    }

    @Test
    public void testCreate() {

        Employee employee = new Employee();
        employee.setName("A");
        employee.setId(1l);
        employee.setCode("EMP-001");
        employee.setDesignation(Designation.ENGINEERING_MANAGER);

        Team team = new Team();
        team.setName("Abc");

        UserRequest userRequest = new UserRequest();
        userRequest.setName("A");
        userRequest.setJob("Job1");


        UserResponse userResponse = new UserResponse();
        userResponse.setId("1");
        userResponse.setName("A");
        userResponse.setJob("Job1");
        userResponse.setCreatedAt("2020-10-05");
        String userResponseString = userResponse.toString();

        Mockito.when(teamRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(team));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Mockito.when(externalAPIService.createUserFromExternalAPI(Mockito.any())).thenReturn(userResponseString);

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("A");
        employeeRequest.setEmployeeDesignation(Designation.ENGINEERING_MANAGER);

        EmployeeResponse employeeResponse = employeeService.create(employeeRequest);
        Assert.assertNotNull(employeeResponse);
    }
}
