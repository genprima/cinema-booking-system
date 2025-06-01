package com.gen.cinema.domain;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.gen.cinema.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;

@MappedSuperclass 
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseEntity implements Auditable, Serializable {

    @Serial
    private static final long serialVersionUID = -7369920601847524273L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    protected Long id;
   
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate = Instant.now();

    @UpdateTimestamp
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false)
    String modifiedBy;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name="deleted", columnDefinition = "boolean default false")
	private boolean deleted;

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    public void prePersist() {
    	this.deleted = Boolean.FALSE;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
     
    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String getUpdatedBy() {
        return getModifiedBy();
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        setModifiedBy(updatedBy);
    }

    public Long getId() {
        return id;
    }
}
