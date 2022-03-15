package com.shivam.employee.repository;

import com.shivam.employee.entity.Team;
import com.shivam.employee.enums.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>,
        JpaSpecificationExecutor<Team>, PagingAndSortingRepository<Team, Long> {

    Optional<Team> findByTypeAndDeletedFalse(final TeamType type);

    Optional<Team> findByIdAndDeletedFalse(final Long id);

}
