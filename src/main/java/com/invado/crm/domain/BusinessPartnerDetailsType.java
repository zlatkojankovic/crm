package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.invado.crm.domain.util.CustomDateTimeDeserializer;
import com.invado.crm.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BusinessPartnerDetailsType.
 */
@Entity
@Table(name = "CB_BUSINESSPARTNERDETAILSTYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessPartnerDetailsType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_entry", nullable = false)
    private DateTime dateEntry;

    @OneToMany(mappedBy = "businessPartnerDetailsType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessPartnerDetails> businessPartnerDetailss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(DateTime dateEntry) {
        this.dateEntry = dateEntry;
    }

    public Set<BusinessPartnerDetails> getBusinessPartnerDetailss() {
        return businessPartnerDetailss;
    }

    public void setBusinessPartnerDetailss(Set<BusinessPartnerDetails> businessPartnerDetailss) {
        this.businessPartnerDetailss = businessPartnerDetailss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessPartnerDetailsType businessPartnerDetailsType = (BusinessPartnerDetailsType) o;

        if (id != null ? !id.equals(businessPartnerDetailsType.id) : businessPartnerDetailsType.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "BusinessPartnerDetailsType{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", dateEntry='" + dateEntry + "'" +
                '}';
    }
}
