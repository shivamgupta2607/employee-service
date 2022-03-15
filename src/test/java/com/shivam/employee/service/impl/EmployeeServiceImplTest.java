package com.shivam.employee.service.impl;

import com.shivam.employee.dto.EmployeeRequest;
import com.shivam.employee.dto.EmployeeResponse;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import com.shivam.employee.repository.EmployeeRepository;
import com.shivam.employee.repository.TeamRepository;
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

        Team team = new Team();
        team.setName("Abc");

        Mockito.when(teamRepository.findByIdAndDeletedFalse(any())).thenReturn(Optional.of(team));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("A");

        EmployeeResponse employeeResponse = employeeService.create(employeeRequest);
        Assert.assertNotNull(employeeResponse);
    }
}
