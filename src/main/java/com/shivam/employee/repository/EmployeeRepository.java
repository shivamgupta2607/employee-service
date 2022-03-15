package com.shivam.employee.repository;

import com.shivam.employee.dto.EmployeeDesignationCounts;
import com.shivam.employee.entity.Employee;
import com.shivam.employee.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee>, PagingAndSortingRepository<Employee, Long> {

    Optional<Employee> findByTeamAndDeletedFalse(final Team team);

    Optional<Employee> findByIdAndDeletedFalse(final Long id);

    @Query(nativeQuery = true, value = "select designation, count(*) from employee where designation = :designation deleted = false group by designation")
    List<EmployeeDesignationCounts> getEmployeeDesignationCounts(@Param("designation") final String designation);

    @Query(nativeQuery = true, value = "select designation, count(*) from employee where deleted = false group by designation")
    List<EmployeeDesignationCounts> getEmployeeDesignationCounts();
}
