package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BusinessPartner.
 */
@Entity
@Table(name="CB_BUSINESSPARTNER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessPartner extends CodeBase implements Serializable {


    @Column(name = "email")
    private String email;

    @Column(name = "pib")
    private String PIB;

    @Column(name = "status")
    private String status;

    @Column(name = "registration_number")
    private String registrationNumber;

    @OneToMany(mappedBy = "businessPartner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessPartnerDetails> businessPartnerDetailss = new HashSet<>();

    @OneToMany(mappedBy = "businessPartner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessPartnerAddress> businessPartnerAddresss = new HashSet<>();

    @OneToMany(mappedBy = "businessPartner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessPartnerContactDetails> businessPartnerContactDetailss = new HashSet<>();

    @OneToMany(mappedBy = "businessPartner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BankAccount> bankAccounts = new HashSet<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPIB() {
        return PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Set<BusinessPartnerDetails> getBusinessPartnerDetailss() {
        return businessPartnerDetailss;
    }

    public void setBusinessPartnerDetailss(Set<BusinessPartnerDetails> businessPartnerDetailss) {
        this.businessPartnerDetailss = businessPartnerDetailss;
    }

    public Set<BusinessPartnerAddress> getBusinessPartnerAddresss() {
        return businessPartnerAddresss;
    }

    public void setBusinessPartnerAddresss(Set<BusinessPartnerAddress> businessPartnerAddresss) {
        this.businessPartnerAddresss = businessPartnerAddresss;
    }

    public Set<BusinessPartnerContactDetails> getBusinessPartnerContactDetailss() {
        return businessPartnerContactDetailss;
    }

    public void setBusinessPartnerContactDetailss(Set<BusinessPartnerContactDetails> businessPartnerContactDetailss) {
        this.businessPartnerContactDetailss = businessPartnerContactDetailss;
    }

    public Set<BankAccount> getBankAccountss() {
        return bankAccounts;
    }

    public void setBankAccountss(Set<BankAccount> bankAccountss) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessPartner businessPartner = (BusinessPartner) o;

        if (super.getId() != null ? !super.getId().equals(businessPartner.getId()) : businessPartner.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (super.getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "BusinessPartner{" +
                "id=" + getId() +
                ", email='" + email + "'" +
                ", PIB='" + PIB + "'" +
                ", status='" + status + "'" +
                ", registrationNumber='" + registrationNumber + "'" +
                '}';
    }
}
