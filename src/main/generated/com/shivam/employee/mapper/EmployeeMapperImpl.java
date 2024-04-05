package com.shivam.employee.mapper;

import com.shivam.employee.dto.request.EmployeeRequest;
import com.shivam.employee.dto.request.TeamRequest;
import com.shivam.employee.dto.response.EmployeeResponse;
import com.shivam.employee.dto.response.TeamResponse;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-05T13:17:09+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public TeamResponse mapToTeamResponse(Team team) {
        if ( team == null ) {
            return null;
        }

        TeamResponse teamResponse = new TeamResponse();

        teamResponse.setName( team.getName() );
        teamResponse.setId( team.getId() );

        return teamResponse;
    }

    @Override
    public List<TeamResponse> mapToTeamResponse(List<Team> teamList) {
        if ( teamList == null ) {
            return null;
        }

        List<TeamResponse> list = new ArrayList<TeamResponse>( teamList.size() );
        for ( Team team : teamList ) {
            list.add( mapToTeamResponse( team ) );
        }

        return list;
    }

    @Override
    public Team mapToTeamCreate(TeamRequest teamRequest) {
        if ( teamRequest == null ) {
            return null;
        }

        Team team = new Team();

        team.setType( teamRequest.getTeamType() );
        team.setName( teamRequest.getName() );

        return team;
    }

    @Override
    public Team mapToTeamEntity(TeamRequest teamRequest, Team team) {
        if ( teamRequest == null ) {
            return null;
        }

        team.setName( teamRequest.getName() );

        return team;
    }

    @Override
    public EmployeeResponse mapToEmployeeResponse(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setName( employee.getName() );
        employeeResponse.setSalary( employee.getSalary() );
        employeeResponse.setJoiningDate( employee.getJoiningDate() );
        employeeResponse.setId( employee.getId() );

        return employeeResponse;
    }

    @Override
    public List<EmployeeResponse> mapToEmployeeResponse(List<Employee> employeeList) {
        if ( employeeList == null ) {
            return null;
        }

        List<EmployeeResponse> list = new ArrayList<EmployeeResponse>( employeeList.size() );
        for ( Employee employee : employeeList ) {
            list.add( mapToEmployeeResponse( employee ) );
        }

        return list;
    }

    @Override
    public Employee mapToEmployeeCreate(EmployeeRequest employeeRequest) {
        if ( employeeRequest == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setDesignation( employeeRequest.getEmployeeDesignation() );
        employee.setName( employeeRequest.getName() );
        employee.setSalary( employeeRequest.getSalary() );
        employee.setJoiningDate( employeeRequest.getJoiningDate() );

        return employee;
    }
}
