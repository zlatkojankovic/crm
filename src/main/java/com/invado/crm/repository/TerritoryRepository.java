package com.invado.crm.repository;

import com.invado.crm.domain.Territory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Territory entity.
 */
public interface TerritoryRepository extends JpaRepository<Territory,Long>{

}
