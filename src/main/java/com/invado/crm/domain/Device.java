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
 * A Device.
 */
@Entity
@Table(name = "T_DEVICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "custom_id")
    private String customId;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "firmsare_version")
    private String firmsareVersion;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_entry", nullable = false)
    private DateTime dateEntry;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date_valid", nullable = false)
    private DateTime dateValid;

    @ManyToOne
    private Item item;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeviceOnPartnerLocation> deviceOnPartnerLocations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirmsareVersion() {
        return firmsareVersion;
    }

    public void setFirmsareVersion(String firmsareVersion) {
        this.firmsareVersion = firmsareVersion;
    }

    public DateTime getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(DateTime dateEntry) {
        this.dateEntry = dateEntry;
    }

    public DateTime getDateValid() {
        return dateValid;
    }

    public void setDateValid(DateTime dateValid) {
        this.dateValid = dateValid;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Set<DeviceOnPartnerLocation> getDeviceOnPartnerLocations() {
        return deviceOnPartnerLocations;
    }

    public void setDeviceOnPartnerLocations(Set<DeviceOnPartnerLocation> deviceOnPartnerLocations) {
        this.deviceOnPartnerLocations = deviceOnPartnerLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Device device = (Device) o;

        if (id != null ? !id.equals(device.id) : device.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", customId='" + customId + "'" +
                ", serialNumber='" + serialNumber + "'" +
                ", status='" + status + "'" +
                ", firmsareVersion='" + firmsareVersion + "'" +
                ", dateEntry='" + dateEntry + "'" +
                ", dateValid='" + dateValid + "'" +
                '}';
    }
}
