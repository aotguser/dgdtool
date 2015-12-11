package com.asp.dgdtool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Package_version.
 */
@Entity
@Table(name = "package_version")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "package_version")
public class Package_version implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "package_version_name", length = 200, nullable = false)
    private String package_version_name;

    @NotNull
    @Column(name = "package_id", nullable = false)
    private Integer package_id;

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

    public String getPackage_version_name() {
        return package_version_name;
    }

    public void setPackage_version_name(String package_version_name) {
        this.package_version_name = package_version_name;
    }

    public Integer getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Integer package_id) {
        this.package_id = package_id;
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

        Package_version package_version = (Package_version) o;

        if ( ! Objects.equals(id, package_version.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Package_version{" +
            "id=" + id +
            ", package_version_name='" + package_version_name + "'" +
            ", package_id='" + package_id + "'" +
            ", description='" + description + "'" +
            ", status_id='" + status_id + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", modified_by='" + modified_by + "'" +
            ", modified_date='" + modified_date + "'" +
            '}';
    }
}
