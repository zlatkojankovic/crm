package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.invado.crm.domain.util.CustomLocalDateSerializer;
import com.invado.crm.domain.util.ISO8601LocalDateDeserializer;
import com.invado.crm.domain.util.CustomDateTimeDeserializer;
import com.invado.crm.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceOnPartnerLocation.
 */
@Entity
@Table(name = "CB_DEVICEONPARTNERLOCATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeviceOnPartnerLocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_to", nullable = false)
    private DateTime dateTo;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Device device;

    @ManyToOne
    private BusinessPartner businesspartner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public BusinessPartner getBusinesspartner() {
        return businesspartner;
    }

    public void setBusinesspartner(BusinessPartner businesspartner) {
        this.businesspartner = businesspartner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceOnPartnerLocation deviceOnPartnerLocation = (DeviceOnPartnerLocation) o;

        if (id != null ? !id.equals(deviceOnPartnerLocation.id) : deviceOnPartnerLocation.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "DeviceOnPartnerLocation{" +
                "id=" + id +
                ", dateFrom='" + dateFrom + "'" +
                ", dateTo='" + dateTo + "'" +
                ", status='" + status + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}
