package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Territory.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Territory extends CodeBase implements Serializable  {

    @ManyToOne
    private TerritoryType territoryType;

    public TerritoryType getTerritoryType() {
        return territoryType;
    }

    public void setTerritoryType(TerritoryType territoryType) {
        this.territoryType = territoryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Territory territory = (Territory) o;

        if (super.getId() != null ? !super.getId().equals(super.getId()) : super.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (super.getId() ^ (super.getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Territory{" +
                "id=" + super.getId() +
                '}';
    }
}
