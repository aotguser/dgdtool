package com.asp.dgdtool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "requestor_id", nullable = false)
    private Integer requestor_id;

    @Column(name = "associated_project_id")
    private Integer associated_project_id;

    @Column(name = "initiative_id")
    private Integer initiative_id;

    @Column(name = "ticket_id")
    private Integer ticket_id;

    @Column(name = "service_id")
    private Integer service_id;

    @Column(name = "app_id")
    private Integer app_id;

    @Column(name = "package_id")
    private Integer package_id;

    @Size(max = 200)
    @Column(name = "legacy_owner", length = 200)
    private String legacy_owner;

    @Size(max = 200)
    @Column(name = "business_unit", length = 200)
    private String business_unit;

    @Column(name = "planned_start_date")
    private ZonedDateTime planned_start_date;

    @Column(name = "planned_end_date")
    private ZonedDateTime planned_end_date;

    @NotNull
    @Size(max = 2000)
    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    @NotNull
    @Column(name = "status_id", nullable = false)
    private Integer status_id;

    @NotNull
    @Size(max = 200)
    @Column(name = "created_by", length = 200, nullable = false)
    private String created_by;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate created_date;

    @NotNull
    @Size(max = 200)
    @Column(name = "modified_by", length = 200, nullable = false)
    private String modified_by;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private LocalDate modified_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestor_id() {
        return requestor_id;
    }

    public void setRequestor_id(Integer requestor_id) {
        this.requestor_id = requestor_id;
    }

    public Integer getAssociated_project_id() {
        return associated_project_id;
    }

    public void setAssociated_project_id(Integer associated_project_id) {
        this.associated_project_id = associated_project_id;
    }

    public Integer getInitiative_id() {
        return initiative_id;
    }

    public void setInitiative_id(Integer initiative_id) {
        this.initiative_id = initiative_id;
    }

    public Integer getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Integer getApp_id() {
        return app_id;
    }

    public void setApp_id(Integer app_id) {
        this.app_id = app_id;
    }

    public Integer getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Integer package_id) {
        this.package_id = package_id;
    }

    public String getLegacy_owner() {
        return legacy_owner;
    }

    public void setLegacy_owner(String legacy_owner) {
        this.legacy_owner = legacy_owner;
    }

    public String getBusiness_unit() {
        return business_unit;
    }

    public void setBusiness_unit(String business_unit) {
        this.business_unit = business_unit;
    }

    public ZonedDateTime getPlanned_start_date() {
        return planned_start_date;
    }

    public void setPlanned_start_date(ZonedDateTime planned_start_date) {
        this.planned_start_date = planned_start_date;
    }

    public ZonedDateTime getPlanned_end_date() {
        return planned_end_date;
    }

    public void setPlanned_end_date(ZonedDateTime planned_end_date) {
        this.planned_end_date = planned_end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public LocalDate getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDate modified_date) {
        this.modified_date = modified_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        if ( ! Objects.equals(id, project.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", requestor_id='" + requestor_id + "'" +
            ", associated_project_id='" + associated_project_id + "'" +
            ", initiative_id='" + initiative_id + "'" +
            ", ticket_id='" + ticket_id + "'" +
            ", service_id='" + service_id + "'" +
            ", app_id='" + app_id + "'" +
            ", package_id='" + package_id + "'" +
            ", legacy_owner='" + legacy_owner + "'" +
            ", business_unit='" + business_unit + "'" +
            ", planned_start_date='" + planned_start_date + "'" +
            ", planned_end_date='" + planned_end_date + "'" +
            ", description='" + description + "'" +
            ", status_id='" + status_id + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", modified_by='" + modified_by + "'" +
            ", modified_date='" + modified_date + "'" +
            '}';
    }
}
