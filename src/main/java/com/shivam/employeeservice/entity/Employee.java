package com.shivam.employeeservice.entity;

import com.shivam.employeeservice.constants.EntityConstants;
import com.shivam.employeeservice.constants.EntityConstants.EmployeeConstants;
import com.shivam.employeeservice.entity.base.BaseEntity;
import com.shivam.employeeservice.entity.converter.PostgresSQLEnumType;
import com.shivam.employeeservice.enums.Designation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/* One employee will have one team*/
@Data
@Entity
@Table(name = EntityConstants.EmployeeConstants.EMPLOYEE_TABLE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TypeDef(
        name = EntityConstants.PGSQL_ENUM,
        typeClass = PostgresSQLEnumType.class
)
public class Employee extends BaseEntity {

    @Column(name = EmployeeConstants.NAME, nullable = false)
    private String name;

    @Column(name = EmployeeConstants.CODE, nullable = false)
    private String code;

    @Column(name = EmployeeConstants.SALARY, nullable = false)
    private BigDecimal salary;

    @Column(name = EmployeeConstants.JOINING_DATE, nullable = false)
    private Date joiningDate;

    @Enumerated(EnumType.STRING)
    @Type(type = EntityConstants.PGSQL_ENUM)
    @Column(name = EmployeeConstants.DESIGNATION, nullable = false)
    private Designation designation;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EmployeeConstants.TEAM_ID, nullable = false)
    private Team team;

}
