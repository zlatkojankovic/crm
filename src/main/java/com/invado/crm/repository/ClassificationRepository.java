package com.invado.crm.repository;

import com.invado.crm.domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Classification entity.
 */
public interface ClassificationRepository extends JpaRepository<Classification,Long>{

}
