package com.invado.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Warehouse.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Warehouse extends CodeBase implements Serializable {

   

    @ManyToOne
    private Territory territory;
   
    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Warehouse warehouse = (Warehouse) o;

        if (this.getId() != null ? !this.getId().equals(warehouse.getId()) : warehouse.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (this.getId() ^ (this.getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + this.getId() +
                '}';
    }
}
