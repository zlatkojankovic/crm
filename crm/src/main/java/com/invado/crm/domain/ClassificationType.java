package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClassificationType.
 */
@Entity
@Table(name = "T_CLASSIFICATIONTYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassificationType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mandatory")
    private Integer mandatory;

    @Column(name = "object_classification_type")
    private String objectClassificationType;

    @OneToMany(mappedBy = "classificationType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Classification> classifications = new HashSet<>();

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

    public Integer getMandatory() {
        return mandatory;
    }

    public void setMandatory(Integer mandatory) {
        this.mandatory = mandatory;
    }

    public String getObjectClassificationType() {
        return objectClassificationType;
    }

    public void setObjectClassificationType(String objectClassificationType) {
        this.objectClassificationType = objectClassificationType;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(Set<Classification> classifications) {
        this.classifications = classifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassificationType classificationType = (ClassificationType) o;

        if (id != null ? !id.equals(classificationType.id) : classificationType.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "ClassificationType{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", mandatory='" + mandatory + "'" +
                ", objectClassificationType='" + objectClassificationType + "'" +
                '}';
    }
}
