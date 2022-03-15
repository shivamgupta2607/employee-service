package com.shivam.employee.entity.base;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shivam.employee.constants.EntityConstants;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Base class which store common fields from entity classes.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {EntityConstants.CREATED_DATE, EntityConstants.UPDATED_DATE},
        allowGetters = true
)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EntityConstants.ID, unique = true, nullable = false)
    private Long id;

    @Column(name = EntityConstants.DELETED_FLG, nullable = false)
    private Boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.CREATED_DATE, nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.UPDATED_DATE, nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = EntityConstants.CREATED_BY)
    @CreatedBy
    private Long createdBy;

    @Column(name = EntityConstants.UPDATED_BY)
    @LastModifiedBy
    private Long updatedBy;

    @PrePersist
    public void prePersist() {
        this.deleted = Boolean.FALSE;
    }

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }
}
