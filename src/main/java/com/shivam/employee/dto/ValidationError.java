package com.shivam.employee.dto;

import lombok.Data;

/**
 * This class holds validation error information.
 */
@Data
public class ValidationError {

    private String field;
    private String error;
}
