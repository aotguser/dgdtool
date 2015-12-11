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
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partner")
public class Partner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "partner_name", length = 200, nullable = false)
    private String partner_name;

    @NotNull
    @Size(max = 400)
    @Column(name = "partner_key", length = 400, nullable = false)
    private String partner_key;

    @NotNull
    @Size(max = 2000)
    @Column(name = "partner_base_url", length = 2000, nullable = false)
    private String partner_base_url;

    @NotNull
    @Size(max = 2000)
    @Column(name = "partner_broker_url", length = 2000, nullable = false)
    private String partner_broker_url;

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

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_key() {
        return partner_key;
    }

    public void setPartner_key(String partner_key) {
        this.partner_key = partner_key;
    }

    public String getPartner_base_url() {
        return partner_base_url;
    }

    public void setPartner_base_url(String partner_base_url) {
        this.partner_base_url = partner_base_url;
    }

    public String getPartner_broker_url() {
        return partner_broker_url;
    }

    public void setPartner_broker_url(String partner_broker_url) {
        this.partner_broker_url = partner_broker_url;
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

        Partner partner = (Partner) o;

        if ( ! Objects.equals(id, partner.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + id +
            ", partner_name='" + partner_name + "'" +
            ", partner_key='" + partner_key + "'" +
            ", partner_base_url='" + partner_base_url + "'" +
            ", partner_broker_url='" + partner_broker_url + "'" +
            ", description='" + description + "'" +
            ", status_id='" + status_id + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", modified_by='" + modified_by + "'" +
            ", modified_date='" + modified_date + "'" +
            '}';
    }
}
