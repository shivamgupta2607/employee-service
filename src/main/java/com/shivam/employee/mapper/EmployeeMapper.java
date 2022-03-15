package com.shivam.employee.mapper;

import com.shivam.employee.constants.AppConstants;
import com.shivam.employee.dto.EmployeeRequest;
import com.shivam.employee.dto.EmployeeResponse;
import com.shivam.employee.dto.TeamRequest;
import com.shivam.employee.dto.TeamResponse;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    TeamResponse mapToTeamResponse(final Team team);

    List<TeamResponse> mapToTeamResponse(final List<Team> teamList);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.DELETED, ignore = true),
            @Mapping(target = "type", source = "teamType")
    })
    Team mapToTeamCreate(final TeamRequest teamRequest);

    @Mappings(value = {
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true)
    })
    Team mapToTeamEntity(final TeamRequest teamRequest,
                         @MappingTarget final Team team);

    EmployeeResponse mapToEmployeeResponse(final Employee employee);

    List<EmployeeResponse> mapToEmployeeResponse(final List<Employee> employeeList);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.DELETED, ignore = true),
            @Mapping(target = "designation", source = "employeeDesignation")
    })
    Employee mapToEmployeeCreate(EmployeeRequest employeeRequest);
}