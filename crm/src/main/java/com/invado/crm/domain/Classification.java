package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Classification.
 */
@Entity
@Table(name = "T_CLASSIFICATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Classification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "classification_segment")
    private String classificationSegment;

    @ManyToOne
    private Classification classification;

    @ManyToMany(mappedBy = "classifications")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CodeBase> codeBases = new HashSet<>();

    @ManyToOne
    private ClassificationType classificationType;

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

    public String getClassificationSegment() {
        return classificationSegment;
    }

    public void setClassificationSegment(String classificationSegment) {
        this.classificationSegment = classificationSegment;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Set<CodeBase> getCodeBases() {
        return codeBases;
    }

    public void setCodeBases(Set<CodeBase> codeBases) {
        this.codeBases = codeBases;
    }

    public ClassificationType getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(ClassificationType classificationType) {
        this.classificationType = classificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Classification classification = (Classification) o;

        if (id != null ? !id.equals(classification.id) : classification.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Classification{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", classificationSegment='" + classificationSegment + "'" +
                '}';
    }
}
