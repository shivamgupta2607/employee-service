package com.shivam.employee.service;

import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.dto.response.UserResponse;

public interface ExternalAPIService {

    UserResponse createUserFromExternalAPI(final UserRequest userRequest);
}
