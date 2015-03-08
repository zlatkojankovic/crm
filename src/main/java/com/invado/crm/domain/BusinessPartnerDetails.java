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
 * A BusinessPartnerDetails.
 */
@Entity
@Table(name = "CB_BUSINESSPARTNERDETAILS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessPartnerDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_from", nullable = false)
    private DateTime dateFrom;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_to", nullable = false)
    private DateTime dateTo;

    @Column(name = "content_version")
    private String contentVersion;

    @ManyToOne
    private BusinessPartner businessPartner;

    @ManyToOne
    private BusinessPartnerDetailsType businessPartnerDetailsType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
    }

    public BusinessPartnerDetailsType getBusinessPartnerDetailsType() {
        return businessPartnerDetailsType;
    }

    public void setBusinessPartnerDetailsType(BusinessPartnerDetailsType businessPartnerDetailsType) {
        this.businessPartnerDetailsType = businessPartnerDetailsType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessPartnerDetails businessPartnerDetails = (BusinessPartnerDetails) o;

        if (id != null ? !id.equals(businessPartnerDetails.id) : businessPartnerDetails.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "BusinessPartnerDetails{" +
                "id=" + id +
                ", content='" + content + "'" +
                ", dateFrom='" + dateFrom + "'" +
                ", dateTo='" + dateTo + "'" +
                ", contentVersion='" + contentVersion + "'" +
                '}';
    }
}
