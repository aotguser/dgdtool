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
 * A Project_team.
 */
@Entity
@Table(name = "project_team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project_team")
public class Project_team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Integer project_id;

    @NotNull
    @Column(name = "resource_id", nullable = false)
    private Integer resource_id;

    @NotNull
    @Column(name = "role_id", nullable = false)
    private Integer role_id;

    @Column(name = "support_level")
    private Integer support_level;

    @Column(name = "est_hours")
    private Integer est_hours;

    @Column(name = "assigned_date", nullable = false)
    private ZonedDateTime assigned_date;

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

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getSupport_level() {
        return support_level;
    }

    public void setSupport_level(Integer support_level) {
        this.support_level = support_level;
    }

    public Integer getEst_hours() {
        return est_hours;
    }

    public void setEst_hours(Integer est_hours) {
        this.est_hours = est_hours;
    }

    public ZonedDateTime getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(ZonedDateTime assigned_date) {
        this.assigned_date = assigned_date;
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

        Project_team project_team = (Project_team) o;

        if ( ! Objects.equals(id, project_team.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project_team{" +
            "id=" + id +
            ", project_id='" + project_id + "'" +
            ", resource_id='" + resource_id + "'" +
            ", role_id='" + role_id + "'" +
            ", support_level='" + support_level + "'" +
            ", est_hours='" + est_hours + "'" +
            ", assigned_date='" + assigned_date + "'" +
            ", description='" + description + "'" +
            ", status_id='" + status_id + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", modified_by='" + modified_by + "'" +
            ", modified_date='" + modified_date + "'" +
            '}';
    }
}
