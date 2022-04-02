package com.shivam.employee.service;

import com.shivam.employee.dto.request.UserRequest;

public interface ExternalAPIService {

    String getTeamFromExternalAPI(final UserRequest userRequest);
}
