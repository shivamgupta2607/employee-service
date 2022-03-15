package com.shivam.employee.service;

import com.shivam.employee.dto.TeamRequest;
import com.shivam.employee.dto.TeamResponse;
import com.shivam.employee.dto.filter.FilterCriteria;
import org.springframework.data.domain.Page;

public interface TeamService {

    Page<TeamResponse> search(final FilterCriteria filterCriteria);

    TeamResponse getById(final Long id);

    TeamResponse create(final TeamRequest teamRequest);
}
