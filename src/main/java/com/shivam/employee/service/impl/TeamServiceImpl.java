package com.shivam.employee.service.impl;

import com.shivam.employee.advice.Loggable;
import com.shivam.employee.constants.EntityConstants;
import com.shivam.employee.dto.TeamRequest;
import com.shivam.employee.dto.TeamResponse;
import com.shivam.employee.dto.filter.FilterCriteria;
import com.shivam.employee.dto.filter.SearchCriteria;
import com.shivam.employee.dto.filter.SearchOperation;
import com.shivam.employee.entity.Team;
import com.shivam.employee.exception.RecordNotFoundException;
import com.shivam.employee.mapper.EmployeeMapper;
import com.shivam.employee.repository.EmployeeRepository;
import com.shivam.employee.repository.TeamRepository;
import com.shivam.employee.repository.specification.builder.TeamSpecificationsBuilder;
import com.shivam.employee.service.TeamService;
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

@Service
@Log4j2
public class TeamServiceImpl implements TeamService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    private final static EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    @Loggable
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Page<TeamResponse> search(final FilterCriteria filterCriteria) {
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
        final TeamSpecificationsBuilder builder = new TeamSpecificationsBuilder(
                filterCriteria.getFullTextSearch(), params);
        final Specification<Team> specification = builder.build();
        final int page = filterCriteria.getPage();
        final int limit = filterCriteria.getLimit();
        final Pageable pageable = PageRequest
                .of(page, limit, Sort.by(Sort.Direction.fromString(filterCriteria.getSortDir()),
                        filterCriteria.getSort()));
        final Page<Team> pagedTeam = this.teamRepository
                .findAll(specification, pageable);
        final List<Team> teams = pagedTeam.getContent();
        final List<TeamResponse> teamResponseList = employeeMapper
                .mapToTeamResponse(teams);
        return new PageImpl<>(teamResponseList, pagedTeam.getPageable(),
                pagedTeam.getTotalElements());
    }

    @Override
    @Loggable
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TeamResponse getById(final Long id) {
        log.debug("Getting team by id {}", id);
        final Optional<Team> teamOptional = this.teamRepository
                .findByIdAndDeletedFalse(id);
        if (!teamOptional.isPresent()) {
            throw new RecordNotFoundException(String.format("Team with Id %s not found", id));
        }
        final Team team = teamOptional.get();
        final TeamResponse teamResponse = employeeMapper.mapToTeamResponse(team);
        return teamResponse;
    }

    @Override
    @Loggable
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TeamResponse create(final TeamRequest teamRequest) {
        final Team team = employeeMapper.mapToTeamCreate(teamRequest);
        this.teamRepository.save(team);
        return employeeMapper.INSTANCE.mapToTeamResponse(team);
    }
}
