package com.shivam.employee.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shivam.employee.enums.Designation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRequest {

    private String name;

    private Designation employeeDesignation;

    private BigDecimal salary;

    private Date joiningDate;

    private Long teamId;
}
