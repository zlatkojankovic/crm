package com.invado.crm.repository;

import com.invado.crm.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Warehouse entity.
 */
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{

}
