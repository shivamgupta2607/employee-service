package com.shivam.employeeservice.repository;

import com.shivam.employeeservice.entity.Team;
import com.shivam.employeeservice.enums.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>,
        JpaSpecificationExecutor<Team>, PagingAndSortingRepository<Team, Long> {

    Optional<Team> findByTypeAndDeletedFalse(final TeamType type);

    Optional<Team> findByIdAndDeletedFalse(final Long id);

}
