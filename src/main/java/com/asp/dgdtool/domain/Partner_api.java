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
 * A Partner_api.
 */
@Entity
@Table(name = "partner_api")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partner_api")
public class Partner_api implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "partner_id", nullable = false)
    private Integer partner_id;

    @NotNull
    @Size(max = 2000)
    @Column(name = "partner_req_url", length = 2000, nullable = false)
    private String partner_req_url;

    @NotNull
    @Size(max = 2000)
    @Column(name = "partner_req_obj", length = 2000, nullable = false)
    private String partner_req_obj;

    @NotNull
    @Size(max = 2000)
    @Column(name = "partner_res_url", length = 2000, nullable = false)
    private String partner_res_url;

    @NotNull
    @Size(max = 200)
    @Column(name = "asp_method", length = 200, nullable = false)
    private String asp_method;

    @Column(name = "asp_app_id")
    private Integer asp_app_id;

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

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_req_url() {
        return partner_req_url;
    }

    public void setPartner_req_url(String partner_req_url) {
        this.partner_req_url = partner_req_url;
    }

    public String getPartner_req_obj() {
        return partner_req_obj;
    }

    public void setPartner_req_obj(String partner_req_obj) {
        this.partner_req_obj = partner_req_obj;
    }

    public String getPartner_res_url() {
        return partner_res_url;
    }

    public void setPartner_res_url(String partner_res_url) {
        this.partner_res_url = partner_res_url;
    }

    public String getAsp_method() {
        return asp_method;
    }

    public void setAsp_method(String asp_method) {
        this.asp_method = asp_method;
    }

    public Integer getAsp_app_id() {
        return asp_app_id;
    }

    public void setAsp_app_id(Integer asp_app_id) {
        this.asp_app_id = asp_app_id;
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

        Partner_api partner_api = (Partner_api) o;

        if ( ! Objects.equals(id, partner_api.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partner_api{" +
            "id=" + id +
            ", partner_id='" + partner_id + "'" +
            ", partner_req_url='" + partner_req_url + "'" +
            ", partner_req_obj='" + partner_req_obj + "'" +
            ", partner_res_url='" + partner_res_url + "'" +
            ", asp_method='" + asp_method + "'" +
            ", asp_app_id='" + asp_app_id + "'" +
            ", description='" + description + "'" +
            ", status_id='" + status_id + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", modified_by='" + modified_by + "'" +
            ", modified_date='" + modified_date + "'" +
            '}';
    }
}
