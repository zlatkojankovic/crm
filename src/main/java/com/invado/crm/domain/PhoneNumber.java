package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PhoneNumber.
 */
@Entity
@Table(name = "CB_PHONENUMBER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PhoneNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private String type;

    @ManyToOne
    private BusinessPartnerContactDetails businessPartnerContactDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BusinessPartnerContactDetails getBusinessPartnerContactDetails() {
        return businessPartnerContactDetails;
    }

    public void setBusinessPartnerContactDetails(BusinessPartnerContactDetails businessPartnerContactDetails) {
        this.businessPartnerContactDetails = businessPartnerContactDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumber phoneNumber = (PhoneNumber) o;

        if (id != null ? !id.equals(phoneNumber.id) : phoneNumber.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", number='" + number + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
