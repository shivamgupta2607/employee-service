package com.shivam.employee.entity;

import com.shivam.employee.constants.EntityConstants;
import com.shivam.employee.constants.EntityConstants.TeamConstants;
import com.shivam.employee.entity.base.BaseEntity;
import com.shivam.employee.entity.converter.PostgresSQLEnumType;
import com.shivam.employee.enums.TeamType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = TeamConstants.TEAM_TABLE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TypeDef(
        name = EntityConstants.PGSQL_ENUM,
        typeClass = PostgresSQLEnumType.class
)
public class Team extends BaseEntity {

    @Column(name = TeamConstants.NAME, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Type(type = EntityConstants.PGSQL_ENUM)
    @Column(name = TeamConstants.TYPE, nullable = false)
    private TeamType type;

}
